package com.til.controller.interview.response;

import com.til.domain.interview.dto.InterviewUuidDto;

public record InterviewUuidResponse(
                                    InterviewUuidDto interviewUuid
) {

    public static InterviewUuidResponse of(InterviewUuidDto interviewUuid) {
        return new InterviewUuidResponse(interviewUuid);
    }
}
