package com.til.controller.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.auth.AuthService;
import com.til.common.response.ApiResponse;
import com.til.controller.auth.response.AuthTokenResponse;
import com.til.domain.auth.dto.AuthTokenDto;
import com.til.domain.auth.enums.AuthSuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthService authService;

    @GetMapping("/reissue")
    public ApiResponse<AuthTokenResponse> reissue(@RequestHeader(AUTHORIZATION_HEADER) String token) {
        AuthTokenDto authTokenDto = authService.reissueToken(token);
        return ApiResponse.ok(AuthSuccessCode.SUCCESS_REISSUE, AuthTokenResponse.of(authTokenDto));
    }
}
