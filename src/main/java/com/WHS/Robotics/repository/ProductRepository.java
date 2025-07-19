package com.WHS.Robotics.repository;

import com.WHS.Robotics.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> findProducts(String search, int limit) {
        String sql = "SELECT * FROM products";
        if (search != null && !search.isEmpty()) {
            sql += " WHERE name = '" + search + "'";
        }
        sql += " ORDER BY created_at DESC FETCH FIRST " + limit + " ROWS ONLY";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            product.setImagePath(rs.getString("image_path"));
            product.setDescription(rs.getString("description"));
            product.setCreatedAt(rs.getTimestamp("created_at"));
            return product;
        }
    }
} 