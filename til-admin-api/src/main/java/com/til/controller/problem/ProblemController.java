package com.til.controller.problem;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

import com.til.common.page.PageParamRequest;
import com.til.controller.problem.request.SearchProblemRequest;
import com.til.controller.problem.request.UpdateProblemRequest;
import com.til.controller.problem.response.ProblemInfoResponse;
import com.til.controller.problem.response.ProblemPageResponse;
import com.til.common.http.response.ApiResponse;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.problem.dto.AdminProblemInfoDto;
import com.til.domain.problem.dto.AdminProblemListDto;
import com.til.domain.problem.dto.ProblemPageDto;
import com.til.domain.problem.enums.ProblemSuccessCode;

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

    @GetMapping("")
    public ApiResponse<ProblemPageResponse> readProblemList(@CurrentUser AuthUserInfoDto adminInfo,
        @ModelAttribute PageParamRequest pageParamRequest, @ModelAttribute SearchProblemRequest searchProblemRequest) {
        log.debug("문제 리스트 조회 요청 - 관리자 ID: {}", adminInfo.id());
        ProblemPageDto<AdminProblemListDto> problemPage = adminProblemService.getProblemList(
            pageParamRequest.toServiceDto(), searchProblemRequest.toServiceDto());
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEM_LIST,
            ProblemPageResponse.of(problemPage.problemList(), problemPage.pageInfo()));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProblemInfoResponse> readProblemInfo(
        @CurrentUser AuthUserInfoDto adminInfo, @PathVariable Long id) {
        log.debug("문제 조회 요청 - 관리자 ID: {}, 문제 ID: {}", adminInfo.id(), id);
        AdminProblemInfoDto adminProblemInfoDto = adminProblemService.getProblemInfo(id);
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEM_INFO, ProblemInfoResponse.of(adminProblemInfoDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateProblem(@CurrentUser AuthUserInfoDto adminInfo,
        @PathVariable Long id,
        @RequestBody @Valid UpdateProblemRequest updateProblemRequest) {
        log.debug("문제 수정 요청 - 관리자 ID: {}, 문제 ID: {}", adminInfo.id(), id);
        adminProblemService.updateProblem(updateProblemRequest.toServiceDto(id));
        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProblem(@CurrentUser AuthUserInfoDto adminInfo, @PathVariable Long id) {
        log.debug("문제 삭제 요청 - 관리자 ID: {}", adminInfo.id());
        adminProblemService.deleteProblem(id);
        return ApiResponse.ok();
    }
}
