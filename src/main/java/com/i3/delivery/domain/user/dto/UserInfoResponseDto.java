package com.i3.delivery.domain.user.dto;

import com.i3.delivery.domain.user.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    private UserRoleEnum role;
}

