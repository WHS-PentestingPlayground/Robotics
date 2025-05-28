package com.WHS.Robotics.repository;

import com.WHS.Robotics.entity.Board;
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
public class BoardRepository {
    private final DataSource dataSource;

    @Autowired
    public BoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Board board) throws SQLException {
        String sql = "INSERT INTO BOARDS (TITLE, CONTENT, USER_ID, CREATED_AT) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setInt(3, board.getUserId());
            pstmt.setTimestamp(4, board.getCreatedAt());
            pstmt.executeUpdate();
        }
    }

    public List<Board> findAll() throws SQLException {
        String sql = "SELECT * FROM BOARDS ORDER BY ID DESC";
        List<Board> boards = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                boards.add(mapRowToBoard(rs));
            }
        }
        return boards;
    }

    public Board findById(int id) throws SQLException {
        String sql = "SELECT * FROM BOARDS WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToBoard(rs);
                }
            }
        }
        return null;
    }

    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM BOARDS WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Board mapRowToBoard(ResultSet rs) throws SQLException {
        Board board = new Board();
        board.setId(rs.getInt("ID"));
        board.setTitle(rs.getString("TITLE"));
        board.setContent(rs.getString("CONTENT"));
        board.setUserId(rs.getInt("USER_ID"));
        board.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        return board;
    }
}

