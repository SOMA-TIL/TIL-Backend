package com.til.domain.interview.dto;

import lombok.Builder;

@Builder
public record CreatingProblemResultDto(
                                       String question,
                                       String gradingCriteria
) {

    public static CreatingProblemResultDto create(String question, String gradingCriteria) {
        return CreatingProblemResultDto.builder()
            .question(question)
            .gradingCriteria(gradingCriteria)
            .build();
    }
}
