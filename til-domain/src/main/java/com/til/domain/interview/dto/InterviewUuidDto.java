package com.til.domain.interview.dto;

import com.til.domain.interview.model.Interview;

import lombok.Builder;

@Builder
public record InterviewUuidDto(
                               String uuid
) {

    public static InterviewUuidDto of(Interview interview) {
        return InterviewUuidDto.builder()
            .uuid(interview.getUuid())
            .build();
    }
}
