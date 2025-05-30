package com.WHS.Robotics.service;

import com.WHS.Robotics.entity.User;
import com.WHS.Robotics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.sql.Timestamp;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    // 비밀번호 업데이트 메서드 (기업 회원 이상 전용)
    public void updatePassword(String username, String newEncodedPassword) throws Exception {
        try {
            // 사용자 존재 여부 확인
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new Exception("사용자를 찾을 수 없습니다.");
            }

            // 기업 회원 이상인지 확인 (business_token 검증)
            if (user.getBusiness_token() == null || user.getBusiness_token().trim().isEmpty()) {
                throw new Exception("기업 회원 이상만 비밀번호 변경이 가능합니다.");
            }

            // 비밀번호 업데이트
            userRepository.updatePassword(username, newEncodedPassword);

        } catch (SQLException e) {
            throw new Exception("데이터베이스 오류로 비밀번호 변경에 실패했습니다: " + e.getMessage());
        } catch (Exception e) {
            throw e; // 다른 예외는 그대로 전달
        }
    }
} 