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
import com.WHS.Robotics.config.auth.PrincipalDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // 마이페이지 (id 파라미터로 접근)
    @GetMapping("/mypage/{id}")
    public String myPage(@PathVariable int id, Model model) {
        try {
            User user = userRepository.findById(id);
            if (user == null) {
                model.addAttribute("error", "존재하지 않는 사용자입니다.");
                return "error"; // 에러 페이지로 이동
            }
            model.addAttribute("user", user);
            return "mypage";
        } catch (Exception e) {
            model.addAttribute("error", "사용자 조회 중 오류가 발생했습니다: " + e.getMessage());
            return "error";
        }
    }

    // 비밀번호 변경 폼 (기업 회원 이상만 접근 가능)
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUSINESS')")
    @GetMapping("/mypage/password")
    public String passwordChangeForm(Authentication authentication, Model model) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        model.addAttribute("user", user);
        return "password";
    }

    // 비밀번호 변경 처리 (기업 회원 이상만 접근 가능)
    // 취약점: username 파라미터를 직접 받아서 사용 (세션 검증 없음)
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUSINESS')")
    @PostMapping("/mypage/password")
    public String changePassword(Authentication authentication,
                               @RequestParam String username,
                               @RequestParam String currentPassword,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               Model model) {
        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            User sessionUser = principalDetails.getUser(); // 현재 로그인한 사용자 정보
            
            // 취약점: 파라미터로 받은 username을 검증 없이 사용
            User targetUser = userRepository.findByUsername(username);
            if (targetUser == null) {
                model.addAttribute("error", "사용자를 찾을 수 없습니다.");
                model.addAttribute("user", sessionUser);
                return "password";
            }

            // 비밀번호 변경 검증 1: 현재 비밀번호 검증
            // 취약점: 현재 비밀번호를 현재 세션 사용자(공격자)의 비밀번호와 비교
            // 세션 캐싱 문제 해결을 위해 DB에서 최신 비밀번호 조회
            User currentSessionUser = userRepository.findByUsername(sessionUser.getUsername());
            if (!passwordEncoder.matches(currentPassword, currentSessionUser.getPassword())) {
                model.addAttribute("error", "<strong>알림:</strong> 비밀번호가 일치하지 않습니다.");
                model.addAttribute("user", sessionUser);
                return "password";
            }

            // 비밀번호 변경 검증 2: 새 비밀번호 규칙 검사 (유효한 비밀번호인지 확인)
            String passwordError = userService.validatePasswordForChange(newPassword);
            if (passwordError != null) {
                model.addAttribute("error", passwordError);
                model.addAttribute("user", sessionUser);
                return "password";
            }

            // 비밀번호 변경 검증 3: 새 비밀번호와 확인 비밀번호 일치 확인 (유효한 비밀번호끼리 일치하는지 확인)
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                model.addAttribute("user", sessionUser);
                return "password";
            }

            // 새 비밀번호 암호화
            String encodedNewPassword = passwordEncoder.encode(newPassword);

            // IDOR 취약점: 대상 사용자(targetUser)의 비밀번호를 업데이트
            userService.updatePassword(targetUser.getUsername(), encodedNewPassword);

            // 성공 시 현재 세션 사용자의 마이페이지로 리다이렉트
            return "redirect:/mypage/" + sessionUser.getId();

        } catch (Exception e) {
            model.addAttribute("error", "비밀번호 변경 중 오류가 발생했습니다: " + e.getMessage());
            // 오류 발생 시 세션 사용자 정보 다시 추가
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            User sessionUser = principalDetails.getUser();
            model.addAttribute("user", sessionUser);
            return "password";
        }
    }
}