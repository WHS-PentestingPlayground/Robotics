package com.WHS.Robotics.controller;

import com.WHS.Robotics.entity.Board;
import com.WHS.Robotics.entity.Comment;
import com.WHS.Robotics.repository.BoardRepository;
import com.WHS.Robotics.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    // 게시글 목록 페이지
    @GetMapping("/board/posts")
    public String posts(Model model) throws Exception {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "posts";
    }

    // 게시글 상세 페이지
    @GetMapping("/board/post")
    public String post(@RequestParam("id") int id, Model model) throws Exception {
        Board board = boardRepository.findById(id);
        List<Comment> comments = commentRepository.findByBoardId(id);
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        return "post";
    }

    // 게시글 작성 페이지
    @GetMapping("/board/newPost")
    public String newPostForm() {
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
}

