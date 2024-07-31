package com.til.controller.problem.request;

import com.til.domain.problem.dto.SolveProblemDto;

import jakarta.validation.constraints.NotBlank;

public record SolveProblemRequest(
                                  @NotBlank(message = "답변은 필수 항목입니다.") String answer
) {

    public SolveProblemDto toServiceDto(Long userId, Long problemId) {
        return SolveProblemDto.builder()
            .userId(userId)
            .problemId(problemId)
            .answer(answer)
            .build();
    }
}
