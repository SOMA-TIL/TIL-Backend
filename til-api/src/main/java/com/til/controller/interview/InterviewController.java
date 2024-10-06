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
import com.til.common.http.auth.annotation.CurrentUser;
import com.til.common.http.response.ApiResponse;
import com.til.controller.interview.request.InterviewCreateRequest;
import com.til.controller.interview.request.InterviewSolveRequest;
import com.til.controller.interview.request.SpeechInterviewCreateRequest;
import com.til.controller.interview.response.InterviewCodeResponse;
import com.til.controller.interview.response.InterviewInfoResponse;
import com.til.controller.interview.response.InterviewResultResponse;
import com.til.controller.interview.response.InterviewStatusResponse;
import com.til.domain.grading.dto.InterviewGradingResultDto;
import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewInfoDto;
import com.til.domain.interview.enums.InterviewSuccessCode;
import com.til.domain.interview.model.InterviewStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interview")
public class InterviewController {

    private final InterviewService interviewService;
    private final GradingService gradingService;

    @PostMapping("/create")
    public ApiResponse<InterviewCodeResponse> createInterview(@CurrentUser Long userId,
        @RequestBody InterviewCreateRequest request) {
        InterviewCodeDto interviewCodeDto = interviewService.createInterview(request.toServiceDto(userId));

        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_INTERVIEW_CREATION, InterviewCodeResponse.of(
            interviewCodeDto));
    }

    @PostMapping("/create-pro")
    public ApiResponse<InterviewCodeResponse> createSpeechInterview(@CurrentUser Long userId,
        @RequestBody SpeechInterviewCreateRequest request) {
        InterviewCodeDto interviewCodeDto = interviewService.createSpeechInterview(request.toServiceDto(userId));

        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_INTERVIEW_CREATION, InterviewCodeResponse.of(
            interviewCodeDto));
    }

    @GetMapping("/{code}")
    public ApiResponse<InterviewInfoResponse> getInterviewInfo(@CurrentUser Long userId, @PathVariable String code) {
        InterviewInfoDto interviewInfoDto = interviewService.getProcessingInterviewInfo(userId, code);

        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_GET_INTERVIEW_INFO, InterviewInfoResponse.of(
            interviewInfoDto));
    }

    @PatchMapping("/{code}/solve")
    public ApiResponse<Void> solveInterviewProblem(@CurrentUser Long userId, @PathVariable String code,
        @RequestBody InterviewSolveRequest request) {
        interviewService.solveInterviewProblem(request.toServiceDto(code, userId));

        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_SOLVE_INTERVIEW_PROBLEM);
    }

    @PostMapping("/{code}/submit")
    public ApiResponse<Void> submitInterview(@CurrentUser Long userId, @PathVariable String code) {
        Long interviewId = interviewService.submitInterview(userId, code);
        gradingService.makeGradingInterview(interviewId);
        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_SUBMIT_INTERVIEW);
    }

    @GetMapping("/{code}/result")
    public ApiResponse<InterviewResultResponse> getResult(@CurrentUser Long userId, @PathVariable String code) {
        InterviewGradingResultDto gradingResult = gradingService.getInterviewGradingResult(userId, code);
        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_GET_INTERVIEW_RESULT, InterviewResultResponse.of(
            gradingResult));
    }

    @GetMapping("/{code}/status")
    public ApiResponse<InterviewStatusResponse> getStatus(@CurrentUser Long userId, @PathVariable String code) {
        InterviewStatus status = interviewService.getInterviewStatus(userId, code);
        return ApiResponse.ok(InterviewStatusResponse.of(status));
    }
}
