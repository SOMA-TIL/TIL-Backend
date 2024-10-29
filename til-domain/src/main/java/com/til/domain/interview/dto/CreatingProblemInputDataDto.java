package com.til.domain.interview.dto;

import lombok.Builder;

@Builder
public record CreatingProblemInputDataDto(
                                          int questionSize,
                                          String portfolio
) {

    public static CreatingProblemInputDataDto of(int questionSize, String portfolio) {
        return CreatingProblemInputDataDto
            .builder()
            .questionSize(questionSize)
            .portfolio(portfolio)
            .build();
    }
}
