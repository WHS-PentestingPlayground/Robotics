package com.WHS.Robotics.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Board {
    private int id;
    private String title;
    private String content;
    private int userId;
    private Timestamp createdAt;
}

