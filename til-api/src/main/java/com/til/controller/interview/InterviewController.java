package com.til.controller.interview;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.grading.GradingService;
import com.til.application.interview.InterviewService;
import com.til.common.annotation.CurrentUser;
import com.til.controller.interview.request.InterviewCreateRequest;
import com.til.controller.interview.request.InterviewSolveRequest;
import com.til.controller.interview.response.InterviewCodeResponse;
import com.til.controller.interview.response.InterviewInfoResponse;
import com.til.controller.interview.response.InterviewResultResponse;
import com.til.controller.interview.response.InterviewStatusResponse;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.grading.dto.InterviewGradingResultDto;
import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewInfoDto;
import com.til.domain.interview.enums.InterviewSuccessCode;
import com.til.domain.interview.model.InterviewStatus;
import com.til.http.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interview")
public class InterviewController {

    private final InterviewService interviewService;
    private final GradingService gradingService;

    @PostMapping("/create")
    public ApiResponse<InterviewCodeResponse> createInterview(@CurrentUser AuthUserInfoDto userInfo,
        @RequestBody InterviewCreateRequest request) {
        InterviewCodeDto interviewCodeDto = interviewService.createInterview(request.toServiceDto(userInfo.id()));

        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_INTERVIEW_CREATION, InterviewCodeResponse.of(
            interviewCodeDto));
    }

    @GetMapping("/{code}")
    public ApiResponse<InterviewInfoResponse> getInterviewInfo(@CurrentUser AuthUserInfoDto userInfo,
        @PathVariable String code) {
        InterviewInfoDto interviewInfoDto = interviewService.getProcessingInterviewInfo(userInfo.id(), code);

        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_GET_INTERVIEW_INFO, InterviewInfoResponse.of(
            interviewInfoDto));
    }

    @PatchMapping("/{code}/solve")
    public ApiResponse<Void> solveInterviewProblem(@CurrentUser AuthUserInfoDto userInfo,
        @PathVariable String code,
        @RequestBody InterviewSolveRequest request) {
        interviewService.solveInterviewProblem(request.toServiceDto(code, userInfo.id()));

        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_SOLVE_INTERVIEW_PROBLEM);
    }

    @PostMapping("/{code}/submit")
    public ApiResponse<Void> submitInterview(@CurrentUser AuthUserInfoDto userInfo,
        @PathVariable String code) {
        Long interviewId = interviewService.submitInterview(userInfo.id(), code);
        gradingService.makeGradingInterview(interviewId);
        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_SUBMIT_INTERVIEW);
    }

    @GetMapping("/{code}/result")
    public ApiResponse<InterviewResultResponse> getResult(@CurrentUser AuthUserInfoDto userInfo,
        @PathVariable String code) {
        InterviewGradingResultDto gradingResult = gradingService.getInterviewGradingResult(userInfo.id(), code);
        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_GET_INTERVIEW_RESULT, InterviewResultResponse.of(
            gradingResult));
    }

    @GetMapping("/{code}/status")
    public ApiResponse<InterviewStatusResponse> getStatus(@CurrentUser AuthUserInfoDto userInfo,
        @PathVariable String code) {
        InterviewStatus status = interviewService.getInterviewStatus(userInfo.id(), code);
        return ApiResponse.ok(InterviewStatusResponse.of(status));
    }
}
