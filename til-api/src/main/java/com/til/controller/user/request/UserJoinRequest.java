package com.til.controller.user.request;

import com.til.domain.user.dto.UserJoinDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserJoinRequest(
                              @NotBlank(message = "이메일은 필수 항목입니다.") @Email String email,
                              @NotBlank(message = "비밀번호는 필수 항목입니다.") String password,
                              @NotBlank(message = "닉네임은 필수 항목입니다.") String nickname
) {

    public UserJoinDto toServiceDto() {
        return UserJoinDto.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .build();
    }
}
