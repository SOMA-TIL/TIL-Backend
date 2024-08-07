package com.til.controller.user.request;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import com.til.domain.user.dto.UserPasswordDto;

public record UserPasswordRequest(
                                  @NotBlank(message = "비밀번호는 필수 입력 값 입니다.") String password,
                                  @NotBlank(message = "변경할 비밀번호는 필수 입력 값 입니다.") String newPassword
) {

    public UserPasswordDto toServiceDto() {
        return UserPasswordDto.builder()
            .password(password)
            .newPassword(newPassword)
            .build();
    }
}
