package com.til.domain.user.dto;

import lombok.Builder;

@Builder
public record UserInfoDto(
                          String email,
                          String nickname
// TODO : avatarUrl 추가
) {

}
