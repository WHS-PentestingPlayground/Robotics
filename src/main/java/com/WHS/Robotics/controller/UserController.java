package com.WHS.Robotics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.WHS.Robotics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.WHS.Robotics.service.UserService;
import com.WHS.Robotics.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.WHS.Robotics.config.auth.PrincipalDetails;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    // /login, /logout은 Spring Security에서 기본적으로 지원
    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // 로그인 폼 뷰 반환
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm() {
        return "register";  // 회원가입 폼 뷰 반환
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String passwordConfirm,
                          @RequestParam String role,
                          Model model) {
        try {
            // 아이디 규칙 체크: 4~16자리, 영문/숫자만 허용
            String usernameError = userService.validateUsername(username);
            if (usernameError != null) {
                model.addAttribute("error", usernameError);
                return "register";
            }
            // 비밀번호 규칙 체크: 8~16자리, 영문 대소문자, 숫자, 특수문자 중 2가지 이상 조합
            String passwordError = userService.validatePassword(password);
            if (passwordError != null) {
                model.addAttribute("error", passwordError);
                return "register";
            }
            // 비밀번호 확인 체크
            if (!password.equals(passwordConfirm)) {
                model.addAttribute("error", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                return "register";
            }
            // 중복 체크
            if (userRepository.findByUsername(username) != null) {
                model.addAttribute("error", "이미 존재하는 아이디입니다.");
                return "register";
            }
            userService.register(username, password, role);
            return "redirect:/login";
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("ORA-17132")) {
                return "redirect:/login";
            }
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // 마이페이지
    @GetMapping("/mypage")
    public String myPage(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
                User user = principalDetails.getUser();
                model.addAttribute("user", user);
                return "mypage";
            }
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "사용자 정보를 불러오는 중 오류가 발생했습니다.");
            return "redirect:/login";
        }
    }
}