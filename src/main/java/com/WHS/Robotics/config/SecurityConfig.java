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
import jakarta.servlet.DispatcherType;

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
                        // /uploads/img/**는 브라우저 접근 허용 그 외 /uploads/**는 차단
                        .requestMatchers("/uploads/img/**").permitAll()
                        .requestMatchers("/uploads/**").denyAll()
                        .requestMatchers("/board/**").hasAnyRole("BUSINESS", "ADMIN")
                        .requestMatchers("/mypage/**").authenticated()
                        .requestMatchers("/mypage/password/**").hasAnyRole("BUSINESS", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
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

