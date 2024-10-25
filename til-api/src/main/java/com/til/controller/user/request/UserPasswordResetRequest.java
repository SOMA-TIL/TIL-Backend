package com.til.controller.user.request;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record UserPasswordResetRequest(
                                       @NotBlank(message = "이메일 주소를 입력해주세요.") String email
) {

}
