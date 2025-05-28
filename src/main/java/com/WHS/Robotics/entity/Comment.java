package com.WHS.Robotics.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Comment {
    private int id;
    private int boardId;
    private int userId;
    private String content;
    private Timestamp createdAt;
}

