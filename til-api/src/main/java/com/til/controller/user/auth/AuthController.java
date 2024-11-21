package com.til.controller.user.auth;

import static com.til.common.http.auth.enums.AuthConstants.AUTHORIZATION_HEADER;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.auth.AuthService;
import com.til.application.user.UserService;
import com.til.common.http.response.ApiResponse;
import com.til.controller.user.auth.request.LoginRequest;
import com.til.controller.user.auth.response.AuthTokenResponse;
import com.til.controller.user.response.UserLoginResponse;
import com.til.domain.auth.dto.AuthTokenDto;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.auth.enums.AuthSuccessCode;
import com.til.domain.user.enums.UserSuccessCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/user/login")
    public ApiResponse<UserLoginResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthUserInfoDto userInfoDto = userService.login(request.toServiceDto());
        AuthTokenDto token = authService.createToken(userInfoDto);

        return ApiResponse.ok(UserSuccessCode.SUCCESS_LOGIN, UserLoginResponse.of(userInfoDto, token));
    }

    @GetMapping("/user/logout")
    public ApiResponse<UserSuccessCode> logout(AuthUserInfoDto userInfo) {
        authService.deleteToken(userInfo.id());
        return ApiResponse.ok(UserSuccessCode.SUCCESS_LOGOUT);
    }

    @GetMapping("/auth/reissue")
    public ApiResponse<AuthTokenResponse> reissue(@RequestHeader(AUTHORIZATION_HEADER) String token) {
        AuthTokenDto authTokenDto = authService.reissueToken(token);
        return ApiResponse.ok(AuthSuccessCode.SUCCESS_REISSUE, AuthTokenResponse.of(authTokenDto));
    }
}
