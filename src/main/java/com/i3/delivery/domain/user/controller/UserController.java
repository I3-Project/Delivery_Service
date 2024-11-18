package com.i3.delivery.domain.user.controller;

import com.i3.delivery.domain.user.dto.*;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import com.i3.delivery.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
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

    // 회원정보목록 조회 ( MANAGER, MASTER )
    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_MASTER')")
    public ResponseEntity<Page<UserAllInfoResponseDto>> getAllUsers(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size
    ) {
        Page<UserAllInfoResponseDto> users = userService.getAllUsers(pageable, size);
        return ResponseEntity.ok(users);
    }

    // 회원정보 조회
    @GetMapping("/users/me")
    public ResponseEntity<UserInfoResponseDto> getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserInfoResponseDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    // 회원정보 수정
    @PatchMapping("/users")
    public ResponseEntity<String> editUserInfo(@Valid @RequestBody UserEditRequestDto requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        userService.editUserInfo(requestDto);

        return ResponseEntity.ok("회원정보 수정 성공");
    }

    // 회원정보 수정 ( MASTER )
    @PatchMapping("/users/{username}")
    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    public ResponseEntity<String> editUserInfoByMaster(
            @PathVariable String username,
            @RequestBody UserEditRequestDto requestDto) {
        userService.editUserInfoByMaster(username, requestDto);
        return ResponseEntity.ok("회원정보 수정 성공");
    }

    // 회원권한 부여 ( MASTER )
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER')")
    @PatchMapping("/users/role")
    public ResponseEntity<String> editUserRole(@RequestBody UserRoleEditRequestDto requestDto) {
        userService.editUserRole(requestDto);
        return ResponseEntity.ok("권한 부여 성공");
    }

    // 회원삭제
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userDetails.getUsername());
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    // 회원삭제( MANAGER, MASTER )
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_MASTER')")
    @DeleteMapping("/users/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

}
