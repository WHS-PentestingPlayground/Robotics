package com.WHS.Robotics.controller;

import com.WHS.Robotics.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

@Controller
public class LoginController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login-process")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM USERS WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String realPasswordHash = rs.getString("password");
                String inputPasswordHash = md5(password);

                if (inputPasswordHash.equals(realPasswordHash)) {
                    // ✅ DB 정보로 User 객체 만들어
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(realPasswordHash);
                    user.setRole(rs.getString("role"));
                    user.setBusiness_token(rs.getString("business_token"));
                    user.setCreated_at(rs.getTimestamp("created_at"));

                    // ✅ User 객체를 principal로 넣어
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            user,   // principal로 User 객체
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

                    return "redirect:/";
                }
            }
            return "redirect:/login?error";
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DataSourceUtils.releaseConnection(conn, dataSource);
            } catch (Exception ignored) {}
        }
    }


    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
