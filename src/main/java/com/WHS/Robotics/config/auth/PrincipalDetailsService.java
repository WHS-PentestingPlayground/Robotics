package com.WHS.Robotics.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.WHS.Robotics.entity.User;
import com.WHS.Robotics.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.sql.SQLException;


@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session (내부 Authentication (내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User userEntity = userRepository.findByUsername(username);
            if (userEntity != null) {
                return new PrincipalDetails(userEntity); // UserDetails 구현체
            }
        } catch (SQLException e) {
            // DB 오류도 UsernameNotFoundException으로 감싸서 던짐
            throw new UsernameNotFoundException("Database error", e);
        }
        // 사용자가 없을 때
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
