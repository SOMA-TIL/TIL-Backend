package com.til.controller.interview;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.interview.InterviewService;
import com.til.common.annotation.CurrentUser;
import com.til.common.response.ApiResponse;
import com.til.controller.interview.request.InterviewCreateRequest;
import com.til.controller.interview.response.InterviewUuidResponse;
import com.til.domain.interview.dto.InterviewUuidDto;
import com.til.domain.interview.enums.InterviewSuccessCode;
import com.til.domain.user.dto.UserInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interview")
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/create")
    public ApiResponse<InterviewUuidResponse> createInterview(@CurrentUser UserInfoDto userInfo,
        @RequestBody InterviewCreateRequest request) {
        InterviewUuidDto interviewUuidDto = interviewService.create(request.toServiceDto(userInfo.id()));

        return ApiResponse.ok(InterviewSuccessCode.SUCCESS_INTERVIEW_CREATION, InterviewUuidResponse.of(
            interviewUuidDto));
    }

}
