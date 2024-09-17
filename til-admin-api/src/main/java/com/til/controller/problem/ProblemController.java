package com.til.controller.problem;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.common.annotation.CurrentAdmin;
import com.til.common.http.response.ApiResponse;
import com.til.domain.auth.dto.AuthUserInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
@Slf4j
public class ProblemController {

    @PostMapping("/register")
    public ApiResponse<Void> createProblem(@CurrentAdmin AuthUserInfoDto adminInfo) {
        // TODO: 문제 등록 로직 구현
        log.debug("문제 등록 요청 - 관리자 ID: {}", adminInfo.id());
        return ApiResponse.ok();
    }
}
