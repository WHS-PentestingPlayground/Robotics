package com.WHS.Robotics.repository;

import com.WHS.Robotics.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private RowMapper<File> rowMapper = new RowMapper<File>() {
        @Override
        public File mapRow(ResultSet rs, int rowNum) throws SQLException {
            File file = new File();
            file.setId(rs.getLong("ID"));
            file.setBoardId(rs.getLong("BOARD_ID"));
            file.setFileName(rs.getString("FILE_NAME"));
            file.setFilePath(rs.getString("FILE_PATH"));
            file.setUploadedAt(rs.getTimestamp("UPLOADED_AT"));
            file.setUploadedBy(rs.getLong("UPLOADED_BY"));
            return file;
        }
    };
    
    public void save(File file) {
        String sql = "INSERT INTO FILES (BOARD_ID, FILE_NAME, FILE_PATH, UPLOADED_AT, UPLOADED_BY) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            file.getBoardId(),
            file.getFileName(),
            file.getFilePath(),
            file.getUploadedAt(),
            file.getUploadedBy()
        );
    }
    
    public List<File> findByBoardId(Long boardId) throws SQLException {
        String sql = "SELECT * FROM FILES WHERE BOARD_ID = ?";
        return jdbcTemplate.query(sql, rowMapper, boardId);
    }
} 