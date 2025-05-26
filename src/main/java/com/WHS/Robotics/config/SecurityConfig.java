package com.WHS.Robotics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured, preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig {

    @Bean 
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/board/**").authenticated() // 인증만 되면 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN 권한만 접근 가능
                        .anyRequest().permitAll() // 나머지 요청은 모두 허용
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").permitAll() // 커스텀 로그인 페이지
                        .loginProcessingUrl("/login") // 로그인 폼의 action 경로
                        .defaultSuccessUrl("/") // 로그인 성공 시 리디렉션될 기본 URL
                );

        return http.build();
    }
}

