package com.i3.delivery.domain.user.service;

import com.i3.delivery.domain.user.dto.*;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.entity.UserRoleEnum;
import com.i3.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .email(requestDto.getEmail())
                .address(requestDto.getAddress())
                .phone(requestDto.getPhone())
                .role(UserRoleEnum.USER)
                .build();

        userRepository.save(user);
        user.setCreatedBy(user.getUsername());

    }


    @Transactional(readOnly = true)
    public Page<UserAllInfoResponseDto> getAllUsers(Pageable pageable, Integer size) {
        return userRepository.findAll(pageable)
                .map(user -> UserAllInfoResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .nickname(user.getNickname())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .address(user.getAddress())
                        .role(user.getRole())
                        .is_deleted(user.getIs_deleted())
                        .createdAt(user.getCreatedAt())
                        .createdBy(user.getCreatedBy())
                        .updatedAt(user.getUpdatedAt())
                        .updatedBy(user.getUpdatedBy())
                        .deletedAt(user.getDeleted_at())
                        .deletedBy(user.getDeleted_by())
                        .build());

    }

    public UserInfoResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return UserInfoResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }

    @Transactional
    public void editUserInfo(UserEditRequestDto requestDto) {
        // 로그인한 유저정보조회
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        user.update(requestDto, passwordEncoder);
        user.setUpdatedBy(username);

    }

    @Transactional
    public void editUserRole(UserRoleEditRequestDto requestDto) {
        String username = requestDto.getUsername();
        String role = requestDto.getRole();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        user.setRole(UserRoleEnum.valueOf(role));
    }
}
