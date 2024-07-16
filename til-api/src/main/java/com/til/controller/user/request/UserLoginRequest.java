package com.til.controller.user.request;

import com.til.domain.user.dto.UserLoginDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
                               @NotBlank(message = "이메일은 필수 항목입니다.") @Email String email,
                               @NotBlank(message = "비밀번호는 필수 항목입니다.") String password
) {

    public UserLoginDto toServiceDto() {
        return UserLoginDto.builder()
            .email(email)
            .password(password)
            .build();
    }
}
