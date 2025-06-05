package com.WHS.Robotics.service;

import com.WHS.Robotics.entity.Board;
import com.WHS.Robotics.entity.Comment;
import com.WHS.Robotics.entity.File;
import com.WHS.Robotics.entity.User;
import com.WHS.Robotics.repository.BoardRepository;
import com.WHS.Robotics.repository.CommentRepository;
import com.WHS.Robotics.repository.FileRepository;
import com.WHS.Robotics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.UUID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletContext;

@Service("boardService")
public class BoardService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    CommentRepository commentRepository;

    // 게시글 리스트에서 boardId -> username 매핑 반환
    public Map<Integer, String> getUsernamesByBoard(List<Board> boards) throws Exception {
        Map<Integer, String> userNamesByBoardId = new HashMap<>();
        for (Board board : boards) {
            User user = userRepository.findById(board.getUserId());
            String username = (user != null) ? user.getUsername() : "알수없음";
            userNamesByBoardId.put(board.getId(), username);
        }
        return userNamesByBoardId;
    }

    // 댓글 리스트에서 commentId -> username 매핑 반환
    public Map<Integer, String> getUsernamesByComment(List<Comment> comments) throws Exception {
        Map<Integer, String> commentUsernames = new HashMap<>();
        for (Comment comment : comments) {
            String commentUsername = "unknown";
            User commentUser = userRepository.findById(comment.getUserId());
            if (commentUser != null) {
                commentUsername = commentUser.getUsername();
            }
            commentUsernames.put(comment.getId(), commentUsername);
        }
        return commentUsernames;
    }

    // 게시글 작성자 username 반환
    public String getUsernameByBoard(Board board) throws Exception {
        if (board != null) {
            User user = userRepository.findById(board.getUserId());
            if (user != null) {
                return user.getUsername();
            }
        }
        return "unknown";
    }

    public void writeNotice(String title, String content, int userId, MultipartFile file, ServletContext servletContext) throws Exception {
        // 게시글 저장
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setUserId(userId);
        board.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        board.setNotice(true);  // 공지사항으로 설정
        boardRepository.save(board);

        // 파일이 있다면 저장
        if (file != null && !file.isEmpty()) {
            String uploadDir = servletContext.getRealPath("/uploads/");
            java.io.File dir = new java.io.File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String originalFilename = file.getOriginalFilename();
            java.io.File dest = new java.io.File(dir, originalFilename);
            file.transferTo(dest);
            com.WHS.Robotics.entity.File fileEntity = new com.WHS.Robotics.entity.File();
            fileEntity.setBoardId((long) board.getId());
            fileEntity.setFileName(originalFilename);
            fileEntity.setFilePath(originalFilename);
            fileEntity.setUploadedAt(new Timestamp(System.currentTimeMillis()));
            fileEntity.setUploadedBy((long) userId);
            fileRepository.save(fileEntity);
        }
    }

    public void updateComment(int commentId, String content) throws Exception {
        commentRepository.updateContent(commentId, content);
    }

    // 게시글 삭제
    public void deletePost(int postId) throws Exception {
        boardRepository.deleteById(postId);
    }

    // 댓글 삭제
    public void deleteComment(int commentId) throws Exception {
        commentRepository.deleteById(commentId);
    }

    // 게시글 수정
    public void editPost(int id, String title, String content, int userId) throws Exception {
        Board board = boardRepository.findById(id);
        if (board != null && board.getUserId() == userId) {
            board.setTitle(title);
            board.setContent(content);
            board.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            boardRepository.update(board);
        }
    }

    // 공지사항 수정 (파일 교체 지원)
    public void editNotice(int id, String title, String content, MultipartFile file, ServletContext servletContext) throws Exception {
        Board board = boardRepository.findById(id);
        if (board != null && board.isNotice()) {
            board.setTitle(title);
            board.setContent(content);
            board.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            boardRepository.update(board);

            // 파일 교체 로직
            if (file != null && !file.isEmpty()) {
                // 기존 파일 삭제 (DB + 실제 파일)
                java.util.List<File> oldFiles = fileRepository.findByBoardId((long) id);
                String uploadDir = servletContext.getRealPath("/uploads/");
                for (File oldFile : oldFiles) {
                    java.io.File f = new java.io.File(uploadDir, oldFile.getFilePath());
                    if (f.exists()) f.delete();
                }
                fileRepository.deleteByBoardId((long) id);
                // 새 파일 저장
                String originalFilename = file.getOriginalFilename();
                java.io.File dest = new java.io.File(uploadDir, originalFilename);
                file.transferTo(dest);
                File fileEntity = new File();
                fileEntity.setBoardId((long) id);
                fileEntity.setFileName(originalFilename);
                fileEntity.setFilePath(originalFilename);
                fileEntity.setUploadedAt(new Timestamp(System.currentTimeMillis()));
                fileEntity.setUploadedBy((long) board.getUserId());
                fileRepository.save(fileEntity);
            }
        }
    }

    public boolean isPostOwner(int postId, int userId) {
        try {
            Board board = boardRepository.findById(postId);
            return board != null && board.getUserId() == userId;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCommentOwner(int commentId, int userId) {
        try {
            Comment comment = commentRepository.findById(commentId);
            return comment != null && comment.getUserId() == userId;
        } catch (Exception e) {
            return false;
        }
    }
} 