package com.til.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.user.UserService;
import com.til.common.response.ApiResponse;
import com.til.controller.user.request.UserJoinRequest;
import com.til.domain.user.enums.UserSuccessCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@PostMapping("/join")
	public ApiResponse join(@RequestBody @Valid UserJoinRequest request) {
		userService.join(request.toServiceDto());
		return ApiResponse.ok(UserSuccessCode.SUCCESS_JOIN);
	}
}
