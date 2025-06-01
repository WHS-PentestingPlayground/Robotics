package com.WHS.Robotics.controller;

import com.WHS.Robotics.entity.Board;
import com.WHS.Robotics.entity.Comment;
import com.WHS.Robotics.entity.File;
import com.WHS.Robotics.entity.User;
import com.WHS.Robotics.repository.BoardRepository;
import com.WHS.Robotics.repository.CommentRepository;
import com.WHS.Robotics.repository.FileRepository;
import com.WHS.Robotics.repository.UserRepository;
import com.WHS.Robotics.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.WHS.Robotics.config.auth.PrincipalDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.sql.SQLException;

@Controller
public class BoardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BoardService boardService;

    private final String UPLOAD_DIR = "uploads/";

    // 게시글 목록 페이지
    @GetMapping("/board/posts")
    public String posts(Model model) throws Exception {
        List<Board> boards = boardRepository.findAll();
        Map<Integer, String> userNamesByBoardId = boardService.getUsernamesByBoard(boards);
        model.addAttribute("boards", boards);
        model.addAttribute("userNamesByBoardId", userNamesByBoardId);
        return "posts";
    }

    // 게시글 상세 페이지
    @GetMapping("/board/post")
    public String post(@RequestParam("id") int id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        Board board = boardRepository.findById(id);
        List<Comment> comments = commentRepository.findByBoardId(id);
    
        // 게시글 작성자 username (게시글 정보용)
        String postWriterUsername = boardService.getUsernameByBoard(board);
    
        // 댓글 작성자 username Map (댓글 목록용)
        Map<Integer, String> commentUsernames = boardService.getUsernamesByComment(comments);
    
        // 현재 로그인한 사용자 정보 (댓글 작성 폼용)
        int loginUserId = principalDetails.getUser().getId();
        String loginUsername = principalDetails.getUser().getUsername();
    
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("loginUsername", loginUsername);
    
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        model.addAttribute("username", postWriterUsername); // 게시글 작성자 username
        model.addAttribute("commentUsernames", commentUsernames);
        return "post";
    }

    // 게시글 작성 페이지
    @GetMapping("/board/newPost")
    public String newPostForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        int loginUserId = principalDetails.getUser().getId();
        String loginUsername = principalDetails.getUser().getUsername();
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("loginUsername", loginUsername);
        return "newPost";
    }

    // 게시글 작성 처리
    @PostMapping("/board/newPost")
    public String newPost(@RequestParam String title,
                          @RequestParam String content,
                          @RequestParam int userId) throws Exception {
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setUserId(userId);
        board.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        boardRepository.save(board);
        return "redirect:/board/posts";
    }

    // 게시글 삭제
    @PostMapping("/board/deletePost")
    public String deletePost(@RequestParam int id) throws Exception {
        boardRepository.deleteById(id);
        return "redirect:/board/posts";
    }

    // 댓글 작성
    @PostMapping("/comment")
    public String writeComment(@RequestParam int boardId,
                               @RequestParam int userId,
                               @RequestParam String content) throws Exception {
        Comment comment = new Comment();
        comment.setBoardId(boardId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentRepository.save(comment);
        return "redirect:/board/post?id=" + boardId;
    }

    // 댓글 삭제
    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam int commentId,
                                @RequestParam int boardId) throws Exception {
        commentRepository.deleteById(commentId);
        return "redirect:/board/post?id=" + boardId;
    }

    // 공지사항 작성 페이지
    @GetMapping("/admin/notice")
    public String noticeForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        int loginUserId = principalDetails.getUser().getId();
        String loginUsername = principalDetails.getUser().getUsername();
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("loginUsername", loginUsername);
        return "notice";
    }

    // 공지사항 작성
    @PostMapping("/admin/notice")
    public String writeNotice(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("userId") int userId,
                            @RequestParam(value = "file", required = false) MultipartFile file) throws SQLException {
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
            try {
                // 업로드 디렉토리 생성
                Path uploadPath = Paths.get(UPLOAD_DIR);
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
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return "redirect:/board/posts";
    }
}

