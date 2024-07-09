package com.til.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.auth.AuthService;
import com.til.application.user.UserService;
import com.til.common.response.ApiResponse;
import com.til.controller.user.request.UserJoinRequest;
import com.til.controller.user.request.UserLoginRequest;
import com.til.controller.user.response.UserTokenResponse;
import com.til.domain.auth.dto.AuthTokenDto;
import com.til.domain.auth.dto.AuthUserInfoDto;
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
	public ApiResponse join(@RequestBody @Valid UserJoinRequest request) {
		userService.join(request.toServiceDto());
		return ApiResponse.ok(UserSuccessCode.SUCCESS_JOIN);
	}

	@PostMapping("/login")
	public ApiResponse login(@RequestBody @Valid UserLoginRequest request) {
		AuthUserInfoDto userInfoDto = userService.login(request.toServiceDto());
		AuthTokenDto token = authService.createToken(userInfoDto);

		return ApiResponse.ok(UserSuccessCode.SUCCESS_LOGIN, UserTokenResponse.of(token));
	}

	@GetMapping("/nickname/{nickname}")
	public ApiResponse checkNickname(@PathVariable String nickname) {
		userService.checkNickname(nickname);
		return ApiResponse.ok(UserSuccessCode.POSSIBLE_NICKNAME);
	}
}
