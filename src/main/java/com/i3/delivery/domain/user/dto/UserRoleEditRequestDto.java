package com.i3.delivery.domain.user.dto;

import com.i3.delivery.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserRoleEditRequestDto {

    private String username;
    private String role;

}
