package com.WHS.Robotics.entity;
import lombok.Data;
import java.sql.Timestamp;

@Data   
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String business_token;
    private Timestamp createdAt;
}