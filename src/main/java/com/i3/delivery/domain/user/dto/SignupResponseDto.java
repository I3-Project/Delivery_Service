package com.i3.delivery.domain.user.dto;

import com.i3.delivery.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String address;

    private UserRoleEnum role = UserRoleEnum.USER;

}
