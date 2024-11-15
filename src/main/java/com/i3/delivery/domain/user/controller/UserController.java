package com.i3.delivery.domain.user.controller;

import com.i3.delivery.domain.user.dto.LoginRequestDto;
import com.i3.delivery.domain.user.dto.SignupRequestDto;
import com.i3.delivery.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            // 에러 메시지 추출
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // 유효성 검사 통과 시
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

//    @PostMapping("/users/login")
//    public ResponseEntity<String> login(LoginRequestDto requestDto, HttpServletResponse res) {
//        try {
//            userService.login(requestDto, res);
//            return ResponseEntity.ok("로그인 성공");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body("로그인 실패: " + e.getMessage());
//        }
//    }


}
