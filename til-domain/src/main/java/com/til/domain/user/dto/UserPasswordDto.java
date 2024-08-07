package com.til.domain.user.dto;

import lombok.Builder;

@Builder
public record UserPasswordDto(
                              String password,
                              String newPassword
) {
}
