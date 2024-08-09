package com.til.controller.interview.response;

import com.til.domain.interview.dto.InterviewCodeDto;

public record InterviewCodeResponse(
                                    InterviewCodeDto interviewCode
) {

    public static InterviewCodeResponse of(InterviewCodeDto interviewCode) {
        return new InterviewCodeResponse(interviewCode);
    }
}
