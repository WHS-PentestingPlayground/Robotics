package com.WHS.Robotics.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class File {
    private Long id;
    private Long boardId;
    private String fileName;
    private String filePath;
    private Timestamp uploadedAt;
    private Long uploadedBy;
} 