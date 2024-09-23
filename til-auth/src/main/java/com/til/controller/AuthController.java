package com.til.controller;

import static com.til.domain.auth.enums.AuthConstants.AUTHORIZATION_HEADER;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.auth.AuthService;
import com.til.application.user.AdminUserService;
import com.til.application.user.UserService;
import com.til.common.http.response.ApiResponse;
import com.til.controller.request.LoginRequest;
import com.til.controller.response.AuthTokenResponse;
import com.til.controller.response.UserLoginResponse;
import com.til.domain.auth.dto.AuthTokenDto;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.auth.enums.AuthSuccessCode;
import com.til.domain.user.enums.UserSuccessCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final AdminUserService adminUserService;

    @PostMapping("/user-login")
    public ApiResponse<UserLoginResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthUserInfoDto userInfoDto = userService.login(request.toServiceDto());
        AuthTokenDto token = authService.createToken(userInfoDto);

        return ApiResponse.ok(UserSuccessCode.SUCCESS_LOGIN, UserLoginResponse.of(userInfoDto, token));
    }

    @PostMapping("/admin-login")
    public ApiResponse<UserLoginResponse> admin(@RequestBody @Valid LoginRequest request) {
        AuthUserInfoDto userInfoDto = adminUserService.login(request.toServiceDto());
        AuthTokenDto token = authService.createToken(userInfoDto);

        return ApiResponse.ok(UserSuccessCode.SUCCESS_LOGIN, UserLoginResponse.of(userInfoDto, token));
    }

    @GetMapping("/logout")
    public ApiResponse<UserSuccessCode> logout(AuthUserInfoDto userInfo) {
        authService.deleteToken(userInfo.id());
        return ApiResponse.ok(UserSuccessCode.SUCCESS_LOGOUT);
    }

    @GetMapping("/reissue")
    public ApiResponse<AuthTokenResponse> reissue(@RequestHeader(AUTHORIZATION_HEADER) String token) {
        AuthTokenDto authTokenDto = authService.reissueToken(token);
        return ApiResponse.ok(AuthSuccessCode.SUCCESS_REISSUE, AuthTokenResponse.of(authTokenDto));
    }
}
