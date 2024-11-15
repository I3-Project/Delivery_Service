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
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // TODO null 을 넣는다기보다, 값으면 null로 유지하게 두기
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


//    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
//        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
//
//        // 사용자 확인
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
//        );
//
//        // 비밀번호 확인
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
//        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
//        jwtUtil.addJwtToCookie(token, res);
//    }

}
