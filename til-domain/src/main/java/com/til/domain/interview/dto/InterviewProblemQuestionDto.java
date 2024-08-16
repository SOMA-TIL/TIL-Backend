package com.til.domain.interview.dto;

import com.til.domain.interview.model.InterviewProblemStatus;

import lombok.Builder;

@Builder
public record InterviewProblemQuestionDto(
                                          Long id,
                                          String answer,
                                          Integer sequence,
                                          InterviewProblemStatus status,
                                          Long problemId,
                                          String question
) {

}
