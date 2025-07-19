package com.WHS.Robotics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 웹 설정 클래스
 * HandlerInterceptor 등록 및 기타 MVC 관련 설정을 담당합니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;
    
    /**
     * 인터셉터를 등록하는 메서드
     * 비밀번호 변경 URL에만 속도 제한 인터셉터를 적용합니다.
     * 
     * @param registry 인터셉터 등록을 위한 레지스트리
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 비밀번호 변경 POST 요청에만 속도 제한 적용
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/mypage/password")  // 적용할 URL 패턴
                .excludePathPatterns("/login", "/logout", "/register");  // 제외할 URL (로그인 관련)
    }
} 