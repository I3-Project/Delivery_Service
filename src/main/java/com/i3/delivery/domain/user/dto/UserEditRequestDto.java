package com.i3.delivery.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditRequestDto {

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

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "유효한 전화번호 형식을 입력해주세요! (예: 010-1234-5678)")
    @NotBlank(message = "전화번호를 입력해주세요!")
    private String phone;

}
