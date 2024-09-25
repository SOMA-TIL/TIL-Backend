package com.til.controller.problem;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.common.http.auth.annotation.CurrentUser;
import com.til.common.http.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
@Slf4j
public class ProblemController {

    @PostMapping("/register")
    public ApiResponse<Void> createProblem(@CurrentUser Long adminId) {
        // TODO: 문제 등록 로직 구현
        log.debug("문제 등록 요청 - 관리자 ID: {}", adminId);
        return ApiResponse.ok();
    }
}
