package com.WHS.Robotics.entity;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class Product {
    private int id;
    private String name;
    private int price;
    private String imagePath;
    private String description;
    private Timestamp createdAt;
}
