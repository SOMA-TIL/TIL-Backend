package com.til.controller.problem.request;

import com.til.domain.problem.dto.UserProblemDto;

import jakarta.validation.constraints.NotBlank;

public record UserProblemRequest(
                                 @NotBlank(message = "답변은 필수 항목입니다.") String answer
) {

    public UserProblemDto toServiceDto(Long userId, Long problemId) {
        return UserProblemDto.builder()
            .userId(userId)
            .problemId(problemId)
            .answer(answer)
            .build();
    }
}
