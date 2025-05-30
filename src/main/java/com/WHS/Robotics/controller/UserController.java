package com.WHS.Robotics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.WHS.Robotics.repository.UserRepository;
import com.WHS.Robotics.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.WHS.Robotics.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import com.WHS.Robotics.config.auth.PrincipalDetails;

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

    // 비밀번호 변경 폼 (기업 회원 이상만 접근 가능)
    @GetMapping("/mypage/password")
    public String passwordChangeForm(Model model) {
        try {
            // 현재 로그인된 사용자 정보 가져오기
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return "redirect:/login";
            }

            PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
            User user = principalDetails.getUser();

            // 기업 회원 이상인지 확인 (business_token이 있는지 체크)
            if (user.getBusiness_token() == null || user.getBusiness_token().trim().isEmpty()) {
                model.addAttribute("error", "기업 회원 이상만 비밀번호 변경이 가능합니다.");
                return "redirect:/mypage";
            }

            // 사용자 정보를 모델에 추가 (폼에서 사용할 수 있도록)
            model.addAttribute("user", user);
            return "password"; // 비밀번호 변경 폼 JSP 페이지

        } catch (Exception e) {
            model.addAttribute("error", "비밀번호 변경 폼을 불러오는 중 오류가 발생했습니다.");
            return "redirect:/mypage";
        }
    }

    // 비밀번호 변경 처리 (기업 회원 이상만 가능)
    @PostMapping("/mypage/password")
    public String changePassword(@RequestParam String currentPassword,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               Model model) {
        try {
            // 현재 로그인된 사용자 정보 가져오기
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return "redirect:/login";
            }

            PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
            User user = principalDetails.getUser();

            // 기업 회원 이상인지 확인 (business_token이 있는지 체크)
            if (user.getBusiness_token() == null || user.getBusiness_token().trim().isEmpty()) {
                model.addAttribute("error", "기업 회원 이상만 비밀번호 변경이 가능합니다.");
                return "redirect:/mypage";
            }

            // 새 비밀번호와 확인 비밀번호가 일치하는지 확인
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                model.addAttribute("user", user);
                return "password"; // 비밀번호 변경 폼 반환
            }

            // 현재 비밀번호가 올바른지 확인
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                model.addAttribute("error", "현재 비밀번호가 올바르지 않습니다.");
                model.addAttribute("user", user);
                return "password"; // 비밀번호 변경 폼 반환
            }

            // 새 비밀번호 암호화
            String encodedNewPassword = passwordEncoder.encode(newPassword);

            // 비밀번호 업데이트
            userService.updatePassword(user.getUsername(), encodedNewPassword);

            model.addAttribute("success", "비밀번호가 성공적으로 변경되었습니다.");
            return "redirect:/mypage";

        } catch (Exception e) {
            model.addAttribute("error", "비밀번호 변경 중 오류가 발생했습니다: " + e.getMessage());
            // 사용자 정보 다시 로드해서 폼에 전달
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
                User user = principalDetails.getUser();
                model.addAttribute("user", user);
            } catch (Exception ex) {
                return "redirect:/mypage";
            }
            return "password"; // 비밀번호 변경 폼 반환
        }
    }
}