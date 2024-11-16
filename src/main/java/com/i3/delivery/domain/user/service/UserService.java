package com.i3.delivery.domain.user.service;

import com.i3.delivery.domain.user.dto.LoginRequestDto;
import com.i3.delivery.domain.user.dto.SignupRequestDto;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.entity.UserRoleEnum;
import com.i3.delivery.domain.user.jwt.JwtUtil;
import com.i3.delivery.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    }



}
