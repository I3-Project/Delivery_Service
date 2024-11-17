package com.i3.delivery.domain.user.controller;

import com.i3.delivery.domain.user.dto.*;
import com.i3.delivery.domain.user.entity.UserRoleEnum;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import com.i3.delivery.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/users")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        // 유효성 검사 통과 시
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 회원정보목록 조회
    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_MASTER')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 회원정보 조회
    @GetMapping("/users/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserResponseDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/users")
    public ResponseEntity<String> editUserInfo(@Valid @RequestBody UserEditRequestDto requestDto, BindingResult bindingResult) {

        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        // 유효성 검사 통과 시
        userService.editUserInfo(requestDto);

        return ResponseEntity.ok("회원정보 수정 성공");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MASTER')")
    @PatchMapping("/users/role")
    public ResponseEntity<String> editUserRole(@RequestBody UserRoleEditRequestDto requestDto) {
        userService.editUserRole(requestDto);
        return ResponseEntity.ok("권한 부여 성공");
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
