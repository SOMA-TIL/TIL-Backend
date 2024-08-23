package com.til.controller.interview.response;

import com.til.domain.interview.model.InterviewStatus;

public record InterviewStatusResponse(
                                      InterviewStatus status
) {

    public static InterviewStatusResponse of(InterviewStatus status) {
        return new InterviewStatusResponse(status);
    }
}
