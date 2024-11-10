package com.til.domain.problem.dto;

public record OthersAnswerDto(
                              Long solvedId,
                              Long problemId,
                              String nickname,
                              String answer
) {
}
