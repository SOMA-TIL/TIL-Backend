package com.til.controller.problem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.grading.GradingService;
import com.til.application.problem.ProblemService;
import com.til.application.problem.SolveProblemService;
import com.til.common.http.auth.annotation.CurrentUser;
import com.til.common.http.response.ApiResponse;
import com.til.common.page.PageParamRequest;
import com.til.controller.problem.request.FavoriteProblemRequest;
import com.til.controller.problem.request.SearchProblemRequest;
import com.til.controller.problem.request.SolveProblemRequest;
import com.til.controller.problem.response.ProblemInfoResponse;
import com.til.controller.problem.response.ProblemOthersAnswerResponse;
import com.til.controller.problem.response.ProblemPageResponse;
import com.til.controller.problem.response.ProblemResultResponse;
import com.til.controller.problem.response.ProblemSubmitHistory;
import com.til.controller.problem.response.SolveProblemResponse;
import com.til.domain.common.dto.PageDto;
import com.til.domain.grading.dto.GradingResultDto;
import com.til.domain.problem.dto.OthersAnswerDto;
import com.til.domain.problem.dto.ProblemOverviewInfoDto;
import com.til.domain.problem.dto.ProblemPublicInfoDto;
import com.til.domain.problem.dto.SubmitResultDto;
import com.til.domain.problem.dto.SubmitStatusDto;
import com.til.domain.problem.enums.ProblemSuccessCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
@Slf4j
public class ProblemController {

    private final ProblemService problemService;
    private final SolveProblemService solveProblemService;
    private final GradingService gradingService;

    @GetMapping("")
    public ApiResponse<ProblemPageResponse> getProblemList(@CurrentUser(required = false) Long userId,
        @ModelAttribute PageParamRequest pageParamRequest, @ModelAttribute SearchProblemRequest searchProblemRequest) {
        log.debug("searchProblemRequest: {}", searchProblemRequest);
        PageDto<ProblemOverviewInfoDto> problemPage = problemService.getProblemOverviewList(userId,
            pageParamRequest.toServiceDto(), searchProblemRequest.toServiceDto());
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEM_LIST,
            ProblemPageResponse.of(problemPage.list(), problemPage.pageInfo()));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProblemInfoResponse> getProblemInfo(
        @CurrentUser(required = false) Long userId, @PathVariable Long id) {
        ProblemPublicInfoDto problemPublicInfo = problemService.getProblemInfo(userId, id);
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEM_INFO, ProblemInfoResponse.of(problemPublicInfo));
    }

    @PostMapping("/{id}/favorite")
    public ApiResponse<Void> favoriteProblem(@CurrentUser Long userId, @PathVariable Long id,
        @RequestBody FavoriteProblemRequest favoriteProblemRequest) {
        problemService.toggleFavorite(favoriteProblemRequest.toServiceDto(userId, id));
        return ApiResponse.ok();
    }

    @PostMapping("/{id}/solve")
    public ApiResponse<SolveProblemResponse> submitAnswer(@CurrentUser Long userId, @PathVariable Long id,
        @RequestBody @Valid SolveProblemRequest solveProblemRequest) {
        SubmitStatusDto submitStatus = solveProblemService.solveProblem(
            solveProblemRequest.toServiceDto(userId, id));
        gradingService.makeGradingUserProblem(submitStatus.submitId(), userId);
        return ApiResponse.ok(SolveProblemResponse.of(submitStatus));
    }

    @GetMapping("/{id}/result")
    public ApiResponse<ProblemResultResponse> getGradingResult(@CurrentUser Long userId, @PathVariable Long id,
        @RequestParam Long submitId) {
        GradingResultDto gradingResult = gradingService.getUserProblemGradingResult(userId, id, submitId);
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_SUBMIT_RESULT, ProblemResultResponse.of(gradingResult));
    }

    @GetMapping("/{id}/history")
    public ApiResponse<ProblemSubmitHistory> getProblemSubmitHistory(@CurrentUser Long userId, @PathVariable Long id) {
        SubmitResultDto submitResult = solveProblemService.getProblemSubmitResult(userId, id);
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_SUBMIT_HISTORY, ProblemSubmitHistory.of(submitResult));
    }

    @GetMapping("/{id}/others")
    public ApiResponse<ProblemOthersAnswerResponse> getProblemOthersAnswer(@CurrentUser Long userId,
        @PathVariable Long id, @ModelAttribute PageParamRequest pageParamRequest) {
        PageDto<OthersAnswerDto> othersAnswerList = solveProblemService.getProblemOthersAnswer(userId, id,
            pageParamRequest.toServiceDto());
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_OTHERS_ANSWER, ProblemOthersAnswerResponse.of(
            othersAnswerList));
    }
}
