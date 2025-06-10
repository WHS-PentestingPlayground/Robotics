package com.WHS.Robotics.repository;

import com.WHS.Robotics.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final DataSource dataSource;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 회원 저장
    public int save(User user) throws SQLException {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE, CREATED_AT) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setTimestamp(4, user.getCreated_at());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // id로 회원 조회
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        }
        return null;
    }

    // 전체 회원 조회
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        List<User> users = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }
        return users;
    }

    // 회원 삭제
    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM USERS WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    // username으로 회원 조회
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        }
        return null;
    }

    // 비밀번호 업데이트 (username으로 식별)
    public boolean updatePassword(String username, String newEncodedPassword) throws SQLException {
        String sql = "UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEncodedPassword);
            pstmt.setString(2, username);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // 업데이트된 행이 있으면 true 반환
        }
    }

    // ResultSet -> User 매핑
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setRole(rs.getString("ROLE"));
        user.setCreated_at(rs.getTimestamp("CREATED_AT"));
        return user;
    }
}
