package com.WHS.Robotics.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 비밀번호 변경 요청에 대한 속도 제한 인터셉터
 * Spring MVC HandlerInterceptor를 활용하여 세션별로 5초 간격 제한을 적용합니다.
 * 
 * 적용 대상: POST /mypage/password
 * 제한 시간: 5초
 * 제한 방식: 세션별 개별 적용
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    
    // 제한 시간 상수 (초)
    private static final int RATE_LIMIT_SECONDS = 5;
    
    // 세션에 저장할 키 이름
    private static final String SESSION_KEY = "lastPasswordChangeAttempt";
    
    /**
     * 요청 처리 전에 실행되는 메서드
     * 비밀번호 변경 POST 요청에 대해 5초 간격 제한을 확인합니다.
     * 
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체  
     * @param handler 핸들러 객체
     * @return true: 요청 계속 진행, false: 요청 중단
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        // POST 요청이고 비밀번호 변경 URL인 경우만 속도 제한 적용
        if ("POST".equals(request.getMethod()) && 
            request.getRequestURI().equals("/mypage/password")) {
            
            HttpSession session = request.getSession();
            Long lastAttemptTime = (Long) session.getAttribute(SESSION_KEY);
            long currentTime = System.currentTimeMillis();
            
            // 이전 시도가 있고, 5초가 지나지 않은 경우
            if (lastAttemptTime != null && 
                (currentTime - lastAttemptTime) < RATE_LIMIT_SECONDS * 1000L) {
                
                // 남은 대기 시간 계산
                long remainingTime = RATE_LIMIT_SECONDS - ((currentTime - lastAttemptTime) / 1000);
                
                // 에러 메시지를 세션에 저장하고 리다이렉트 (Flash Message 패턴)
                session.setAttribute("flashError", 
                    "<strong>알림:</strong> 너무 빠른 요청입니다. " + remainingTime + "초 후에 다시 시도해주세요.");
                
                // 리다이렉트로 GET 요청 유도 (PRG 패턴)
                response.sendRedirect(request.getContextPath() + "/mypage/password");
                
                return false; // 요청 처리 중단
            }
            
            // 제한 시간이 지났거나 첫 요청인 경우: 현재 시간을 세션에 저장
            session.setAttribute(SESSION_KEY, currentTime);
        }
        
        return true; // 요청 계속 처리
    }
} 