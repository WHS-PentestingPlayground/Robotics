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

@Service
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

    public void writeNotice(String title, String content, int userId, MultipartFile file, String uploadDir) throws Exception {
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
            // 업로드 디렉토리 생성
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일명 중복 방지를 위해 UUID 사용
            String originalFilename = file.getOriginalFilename();
            String storedFilename = UUID.randomUUID().toString() + "_" + originalFilename;

            // 파일 저장
            Path filePath = uploadPath.resolve(storedFilename);
            Files.copy(file.getInputStream(), filePath);

            // DB에 파일 정보 저장
            File fileEntity = new File();
            fileEntity.setBoardId((long) board.getId());
            fileEntity.setFileName(originalFilename);
            fileEntity.setFilePath(storedFilename);
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
} 