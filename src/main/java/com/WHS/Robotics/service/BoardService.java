package com.WHS.Robotics.service;

import com.WHS.Robotics.entity.Board;
import com.WHS.Robotics.entity.Comment;
import com.WHS.Robotics.entity.User;
import com.WHS.Robotics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    private UserRepository userRepository;

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
} 