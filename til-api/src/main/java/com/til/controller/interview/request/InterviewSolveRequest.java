package com.til.controller.interview.request;

import com.til.domain.interview.dto.InterviewSolveDto;

public record InterviewSolveRequest(
                                    Integer sequence,
                                    String answer
) {

    public InterviewSolveDto toServiceDto(String code, Long userId) {
        return InterviewSolveDto.builder()
            .code(code)
            .sequence(sequence)
            .answer(answer)
            .userId(userId)
            .build();
    }
}
