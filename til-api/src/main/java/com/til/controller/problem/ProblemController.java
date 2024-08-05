package com.til.controller.problem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.problem.ProblemService;
import com.til.application.problem.SolveProblemService;
import com.til.common.annotation.CurrentUser;
import com.til.common.page.PageParamRequest;
import com.til.common.response.ApiResponse;
import com.til.controller.problem.request.FavoriteProblemRequest;
import com.til.controller.problem.request.SolveProblemRequest;
import com.til.controller.problem.response.ProblemInfoResponse;
import com.til.controller.problem.response.ProblemPageResponse;
import com.til.controller.problem.response.SolveProblemResponse;
import com.til.domain.problem.dto.ProblemInfoDto;
import com.til.domain.problem.dto.ProblemPageDto;
import com.til.domain.problem.dto.SolveProblemResultDto;
import com.til.domain.problem.enums.ProblemSuccessCode;
import com.til.domain.user.dto.UserInfoDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;
    private final SolveProblemService solveProblemService;

    @GetMapping("")
    public ApiResponse<ProblemPageResponse> getProblemList(
        @ModelAttribute PageParamRequest pageParamRequest) {
        pageParamRequest.validate();
        ProblemPageDto problemPageDto = problemService.getProblemList(pageParamRequest.toServiceDto());
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEM_LIST, ProblemPageResponse.of(problemPageDto
            .problemList(), problemPageDto.pageInfo()));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProblemInfoResponse> getProblemInfo(@PathVariable Long id) {
        ProblemInfoDto problemInfoDto = problemService.getProblemInfo(id);
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEM_INFO, ProblemInfoResponse.of(problemInfoDto));
    }

    @PostMapping("/{id}/favorite")
    public ApiResponse<Void> favoriteProblem(@CurrentUser UserInfoDto userInfo, @PathVariable Long id,
        @RequestBody FavoriteProblemRequest favoriteProblemRequest) {
        problemService.toggleFavorite(favoriteProblemRequest.toServiceDto(userInfo.id(), id));
        return ApiResponse.ok();
    }

    @PostMapping("/{id}/solve")
    public ApiResponse<SolveProblemResponse> submitAnswer(@CurrentUser UserInfoDto userInfo, @PathVariable Long id,
        @RequestBody @Valid SolveProblemRequest solveProblemRequest) {
        SolveProblemResultDto solveProblemResultDto = solveProblemService.solveProblem(solveProblemRequest.toServiceDto(
            userInfo.id(), id));
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_SOLVE_PROBLEM, SolveProblemResponse.of(solveProblemResultDto));
    }
}
