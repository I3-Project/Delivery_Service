package com.i3.delivery.domain.user.service;

import com.i3.delivery.domain.user.dto.SignupRequestDto;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.entity.UserRoleEnum;
import com.i3.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(
                requestDto.getUsername(),
                encodedPassword,
                requestDto.getNickname(),
                requestDto.getEmail(),
                requestDto.getAddress(),
                UserRoleEnum.USER,
                null,
                null,
                null
        );
        userRepository.save(user);
    }


}
