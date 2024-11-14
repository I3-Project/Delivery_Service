package com.i3.delivery.domain.user.controller;

import com.i3.delivery.domain.user.dto.SignupRequestDto;
import com.i3.delivery.domain.user.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("입력값 오류: " + bindingResult.getAllErrors());
        }

        // 유효성 검사 통과 시
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }


}
