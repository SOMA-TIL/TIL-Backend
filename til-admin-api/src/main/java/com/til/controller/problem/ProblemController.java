package com.til.controller.problem;

import org.springframework.web.bind.annotation.PathVariable;
import com.til.application.problem.AdminProblemService;
import com.til.common.http.auth.annotation.CurrentUser;
import com.til.controller.problem.request.CreateProblemRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.controller.problem.request.UpdateProblemRequest;
import com.til.common.http.response.ApiResponse;
import com.til.domain.auth.dto.AuthUserInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
@Slf4j
public class ProblemController {

    private final AdminProblemService adminProblemService;

    @PostMapping("")
    public ApiResponse<Void> createProblem(@CurrentUser AuthUserInfoDto adminInfo, @RequestBody @Valid
    CreateProblemRequest createProblemRequest) {
        log.debug("문제 등록 요청 - 관리자 ID: {}", adminInfo.id());
        adminProblemService.createProblem(createProblemRequest.toServiceDto());
        return ApiResponse.ok();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateProblem(@CurrentUser AuthUserInfoDto adminInfo,
        @PathVariable Long id,
        @RequestBody @Valid UpdateProblemRequest updateProblemRequest) {
        log.debug("문제 수정 요청 - 관리자 ID: {}, 문제 ID: {}", adminInfo.id(), id);
        adminProblemService.updateProblem(updateProblemRequest.toServiceDto(id));
        return ApiResponse.ok();
    }
}
