package com.i3.delivery.domain.user.dto;

import com.i3.delivery.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Size(min = 4, max = 10, message = "이름은 최소 4자 이상 10자 이하를 입력해주세요!")
    @Pattern(regexp = "^[a-z0-9]+$", message = "이름은 알파벳 소문자와 숫자만 입력 가능합니다!")
    @NotBlank(message = "이름을 입력해주세요!")
    private String username;

    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상 15자 이하를 입력해주세요!")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+=-]+$", message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 포함해야 합니다!")
    @NotBlank(message = "비밀번호를 입력해주세요!")
    private String password;

    @Size(min = 4, max = 10, message = "닉네임은 최소 4자 이상 10자 이하를 입력해주세요!")
    @NotBlank(message = "닉네임을 입력해주세요!")
    private String nickname;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "유효한 이메일을 입력해주세요!")
    @NotBlank(message = "이메일을 입력해주세요!")
    private String email;

    @Size(min = 4, message = "주소는 최소 4자 이상이어야 합니다.")
    @NotBlank(message = "주소를 입력해주세요!")
    private String address;


}
