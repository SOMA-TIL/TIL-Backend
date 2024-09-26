package com.til.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.auth.AuthService;
import com.til.application.user.UserService;
import com.til.common.http.auth.annotation.CurrentUser;
import com.til.common.http.response.ApiResponse;
import com.til.controller.user.request.UserJoinRequest;
import com.til.controller.user.request.UserNicknameRequest;
import com.til.controller.user.request.UserPasswordRequest;
import com.til.controller.user.response.UserInfoResponse;
import com.til.domain.user.enums.UserSuccessCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/join")
    public ApiResponse<Void> join(@RequestBody @Valid UserJoinRequest request) {
        userService.join(request.toServiceDto());
        return ApiResponse.ok(UserSuccessCode.SUCCESS_JOIN);
    }

    @GetMapping("/check-nickname/{nickname}")
    public ApiResponse<Void> checkNickname(@PathVariable String nickname) {
        userService.checkNickname(nickname);
        return ApiResponse.ok(UserSuccessCode.POSSIBLE_NICKNAME);
    }

    @PatchMapping("/change-nickname")
    public ApiResponse<Void> changeNickname(@CurrentUser Long userId, @RequestBody @Valid UserNicknameRequest request) {
        userService.changeNickname(userId, request.nickname());
        return ApiResponse.ok(UserSuccessCode.SUCCESS_CHANGE_NICKNAME);
    }

    @PatchMapping("/change-password")
    public ApiResponse<Void> changePassword(@CurrentUser Long userId, @RequestBody @Valid UserPasswordRequest request) {
        userService.changePassword(userId, request.toServiceDto());
        return ApiResponse.ok(UserSuccessCode.SUCCESS_CHANGE_PASSWORD);
    }

    @GetMapping("/my-info")
    public ApiResponse<UserInfoResponse> getUserInfo(@CurrentUser Long userId) {
        return ApiResponse.ok(UserInfoResponse.of(userService.getUserInfo(userId)));
    }
}
