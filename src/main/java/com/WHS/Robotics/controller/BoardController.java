package com.WHS.Robotics.controller;

import com.WHS.Robotics.entity.Board;
import com.WHS.Robotics.entity.Comment;
import com.WHS.Robotics.repository.BoardRepository;
import com.WHS.Robotics.repository.CommentRepository;
import com.WHS.Robotics.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.WHS.Robotics.config.auth.PrincipalDetails;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.WHS.Robotics.repository.FileRepository;
import jakarta.servlet.ServletContext;
import com.WHS.Robotics.util.FileUploadFilter;

@Controller
public class BoardController {


    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ServletContext servletContext;

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
        String loginUserRole = principalDetails.getUser().getRole();
    
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("loginUsername", loginUsername);
        model.addAttribute("loginUserRole", loginUserRole);
    
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        model.addAttribute("username", postWriterUsername); // 게시글 작성자 username
        model.addAttribute("commentUsernames", commentUsernames);
        // 첨부 파일 정보 추가
        List<com.WHS.Robotics.entity.File> attachedFiles = fileRepository.findByBoardId((long) id);

        for (com.WHS.Robotics.entity.File file : attachedFiles) {
            String path = file.getFilePath();
            if (path != null && path.endsWith("/")) {
                file.setFilePath(path.substring(0, path.length() - 1));
            }
        }
        model.addAttribute("attachedFiles", attachedFiles);
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
    @PreAuthorize("hasRole('ADMIN') or @boardService.isPostOwner(#id, principal.user.id)")
    @PostMapping("/board/deletePost")
    public String deletePost(@RequestParam int id) throws Exception {
        boardService.deletePost(id);
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
    @PreAuthorize("hasRole('ADMIN') or @boardService.isCommentOwner(#commentId, principal.user.id)")
    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam int commentId,
                                @RequestParam int boardId) throws Exception {
        boardService.deleteComment(commentId);
        return "redirect:/board/post?id=" + boardId;
    }

    // 댓글 수정
    @PostMapping("/updateComment")
    public String updateComment(@RequestParam int commentId,
                                @RequestParam String content,
                                @RequestParam int boardId) throws Exception {
        boardService.updateComment(commentId, content);
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
                              @RequestParam(value = "file", required = false) MultipartFile file,
                              @RequestParam(value = "path", required = false) String path,
                              Model model) throws Exception {
        String filePath = null;
        try {
            filePath = FileUploadFilter.validateAndSaveImage(file, path, servletContext);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("loginUserId", userId);
            model.addAttribute("title", title);
            model.addAttribute("content", content);
            return "notice";
        }
        boardService.writeNotice(title, content, userId, filePath);
        return "redirect:/board/posts";
    }

    // 게시글 수정 폼
    @GetMapping("/board/editPost")
    public String editPostForm(@RequestParam int id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        Board board = boardRepository.findById(id);
        int loginUserId = principalDetails.getUser().getId();
        String loginUsername = principalDetails.getUser().getUsername();
        model.addAttribute("board", board);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("loginUsername", loginUsername);
        return "editPost";
    }

    // 게시글 수정 처리
    @PreAuthorize("@boardService.isPostOwner(#id, principal.user.id)")
    @PostMapping("/board/editPost")
    public String editPost(@RequestParam int id,
                           @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam int userId) throws Exception {
        boardService.editPost(id, title, content, userId);
        return "redirect:/board/post?id=" + id;
    }

    // 공지사항 수정 폼
    @GetMapping("/board/editNotice")
    public String editNoticeForm(@RequestParam int id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        Board notice = boardRepository.findById(id);
        int loginUserId = principalDetails.getUser().getId();
        String loginUsername = principalDetails.getUser().getUsername();
        model.addAttribute("notice", notice);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("loginUsername", loginUsername);
        // 첨부파일 정보 추가
        java.util.List<com.WHS.Robotics.entity.File> attachedFiles = fileRepository.findByBoardId((long) id);
        model.addAttribute("attachedFiles", attachedFiles);
        return "editNotice";
    }

    // 공지사항 수정 처리
    @PreAuthorize("@boardService.isPostOwner(#id, principal.user.id)")
    @PostMapping("/board/editNotice")
    public String editNotice(@RequestParam int id,
                            @RequestParam String title,
                            @RequestParam String content,
                            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        boardService.editNotice(id, title, content, file, servletContext);
        return "redirect:/board/post?id=" + id;
    }
}

