package com.WHS.Robotics.repository;

import com.WHS.Robotics.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepository {
    private final DataSource dataSource;

    @Autowired
    public CommentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Comment comment) throws SQLException {
        String sql = "INSERT INTO COMMENTS (BOARD_ID, USER_ID, CONTENT, CREATED_AT) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, comment.getBoardId());
            pstmt.setInt(2, comment.getUserId());
            pstmt.setString(3, comment.getContent());
            pstmt.setTimestamp(4, comment.getCreatedAt());
            pstmt.executeUpdate();
        }
    }

    public List<Comment> findByBoardId(int boardId) throws SQLException {
        String sql = "SELECT * FROM COMMENTS WHERE BOARD_ID = ? ORDER BY ID ASC";
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, boardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    comments.add(mapRowToComment(rs));
                }
            }
        }
        return comments;
    }

    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM COMMENTS WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Comment mapRowToComment(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("ID"));
        comment.setBoardId(rs.getInt("BOARD_ID"));
        comment.setUserId(rs.getInt("USER_ID"));
        comment.setContent(rs.getString("CONTENT"));
        comment.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        return comment;
    }
}

