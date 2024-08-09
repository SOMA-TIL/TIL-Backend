package com.til.controller.user.request;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record UserNicknameRequest(
                                  @NotBlank(message = "닉네임을 입력해주세요.") String nickname
) {

}
