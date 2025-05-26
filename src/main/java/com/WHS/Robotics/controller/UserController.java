package com.WHS.Robotics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.WHS.Robotics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.WHS.Robotics.service.UserService;


@Controller
@RequestMapping
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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
                          @RequestParam String role,
                          Model model) {
        try {
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


}