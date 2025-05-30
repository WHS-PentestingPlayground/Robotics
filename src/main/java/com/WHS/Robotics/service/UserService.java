package com.WHS.Robotics.service;

import com.WHS.Robotics.entity.User;
import com.WHS.Robotics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.sql.Timestamp;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(String username, String password, String role) throws Exception {
        User user = new User();

        // 비밀번호 암호화
        String encodedPwd = passwordEncoder.encode(password);
        user.setUsername(username);
        user.setPassword(encodedPwd);
        user.setRole(role);
        
        // 기업회원이나 관리자계정 경우 기업회원용 business_token 랜덤으로 생성
        if ("BUSINESS".equals(role) || "ADMIN".equals(role)) {
             user.setBusiness_token(java.util.UUID.randomUUID().toString());
         } else {
             user.setBusiness_token(null);
         }

//        // 실제 시나리오에선 이런식으로 기업/관리자 회원가입 막을 예정, 테스트를 위해 개발중엔 풀어놓음
//            throw new Exception("기업 회원가입은 보안팀으로 문의해주세요");
//        }
//        user.setBusiness_token(null);

        user.setCreated_at(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    // 아이디 규칙 검사: 4~16자리, 영문/숫자만 허용
    public String validateUsername(String username) {
        if (username == null || !username.matches("^[a-zA-Z0-9]{4,16}$")) {
            return "아이디는 4~16자리의 영문 또는 숫자만 사용할 수 있습니다.";
        }
        return null;
    }

    // 비밀번호 규칙 검사: 8~16자리, 영문 대소문자/숫자/특수문자 중 2가지 이상 조합
    public String validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 16) {
            return "비밀번호는 8~16자리여야 합니다.";
        }
        int count = 0;
        if (password.matches(".*[a-zA-Z].*")) count++;
        if (password.matches(".*[0-9].*")) count++;
        if (password.matches(".*[^a-zA-Z0-9].*")) count++;
        if (count < 2) {
            return "비밀번호는 영문 대소문자/숫자/특수문자 중 2가지 이상 조합이어야 합니다.";
        }
        return null;
    }
} 