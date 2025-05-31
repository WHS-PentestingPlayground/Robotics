package com.WHS.Robotics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import com.WHS.Robotics.config.auth.PrincipalDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured, preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig {

    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @Bean 
    public PasswordEncoder encodePwd() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] digest = md.digest(rawPassword.toString().getBytes());
                    StringBuilder sb = new StringBuilder();
                    for (byte b : digest) {
                        sb.append(String.format("%02x", b));
                    }
                    return sb.toString();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/board/**").authenticated() // 인증만 되면 접근 가능
                        .requestMatchers("/mypage/**").authenticated() // 인증만 되면 접근 가능
                        .requestMatchers("/mypage/password/**").hasAnyRole("BUSINESS", "ADMIN") // BUSINESS, ADMIN 권한만 접근 가능 (계층 구조 설정 시 수정 가능)
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN 권한만 접근 가능
                        .anyRequest().permitAll() // 나머지 요청은 모두 허용
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").permitAll() // 커스텀 로그인 페이지
                        .loginProcessingUrl("/login") // 로그인 폼의 action 경로
                        .defaultSuccessUrl("/") // 로그인 성공 시 리디렉션될 기본 URL
                        .failureUrl("/login?error=true") // 로그인 실패 시 쿼리 파라미터 전달
                )
                .userDetailsService(principalDetailsService);
                        
        return http.build();
    }
}

