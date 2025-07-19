package com.WHS.Robotics.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.owasp.encoder.Encode;

/**
 * XSS 공격 방지를 위한 유틸리티 클래스
 */
public class XssFilter {
    
    /**
     * HTML 태그를 제거하고 텍스트만 추출하여 XSS 방지
     * @param input 사용자 입력 문자열
     * @return XSS 방지된 문자열
     */
    public static String cleanHtml(String input) {
        if (input == null) {
            return null;
        }
        
        // Jsoup을 사용하여 HTML 태그 제거 및 텍스트만 추출
        return Jsoup.clean(input, Safelist.none());
    }
    
    /**
     * HTML 인코딩을 통해 XSS 방지
     * @param input 사용자 입력 문자열
     * @return HTML 인코딩된 문자열
     */
    public static String encodeHtml(String input) {
        if (input == null) {
            return null;
        }
        
        // OWASP Encoder를 사용하여 HTML 인코딩
        return Encode.forHtml(input);
    }
    
    /**
     * JavaScript 인코딩을 통해 XSS 방지
     * @param input 사용자 입력 문자열
     * @return JavaScript 인코딩된 문자열
     */
    public static String encodeJavaScript(String input) {
        if (input == null) {
            return null;
        }
        
        // OWASP Encoder를 사용하여 JavaScript 인코딩
        return Encode.forJavaScript(input);
    }
    
    /**
     * URL 인코딩을 통해 XSS 방지
     * @param input 사용자 입력 문자열
     * @return URL 인코딩된 문자열
     */
    public static String encodeUrl(String input) {
        if (input == null) {
            return null;
        }
        
        // URL 인코딩 (OWASP Encoder 대신 Java 내장 기능 사용)
        try {
            return java.net.URLEncoder.encode(input, "UTF-8");
        } catch (Exception e) {
            return input;
        }
    }
    
    /**
     * CSS 인코딩을 통해 XSS 방지
     * @param input 사용자 입력 문자열
     * @return CSS 인코딩된 문자열
     */
    public static String encodeCss(String input) {
        if (input == null) {
            return null;
        }
        
        // OWASP Encoder를 사용하여 CSS 인코딩
        return Encode.forCssString(input);
    }
    
    /**
     * 제목과 내용에 대한 XSS 방지 처리
     * @param title 제목
     * @param content 내용
     * @return XSS 방지된 제목과 내용 배열 [title, content]
     */
    public static String[] sanitizeTitleAndContent(String title, String content) {
        String sanitizedTitle = cleanHtml(title);
        String sanitizedContent = cleanHtml(content);
        
        return new String[]{sanitizedTitle, sanitizedContent};
    }
    
    /**
     * 댓글 내용에 대한 XSS 방지 처리
     * @param content 댓글 내용
     * @return XSS 방지된 댓글 내용
     */
    public static String sanitizeComment(String content) {
        return cleanHtml(content);
    }
    
    /**
     * XSS 공격 패턴이 포함되어 있는지 검사
     * @param input 검사할 문자열
     * @return XSS 패턴이 포함되어 있으면 true
     */
    public static boolean containsXssPattern(String input) {
        if (input == null) {
            return false;
        }
        
        String lowerInput = input.toLowerCase();
        
        // 일반적인 XSS 패턴들
        String[] xssPatterns = {
            "<script", "javascript:", "onload=", "onerror=", "onclick=", 
            "onmouseover=", "onfocus=", "onblur=", "onchange=", "onsubmit=",
            "eval(", "document.cookie", "window.location", "alert(", "confirm(",
            "prompt(", "innerHTML", "outerHTML", "document.write", "document.writeln"
        };
        
        for (String pattern : xssPatterns) {
            if (lowerInput.contains(pattern)) {
                return true;
            }
        }
        
        return false;
    }
} 