package com.til.domain.interview.dto;

import com.til.domain.interview.model.InterviewProblemStatus;

import lombok.Builder;

@Builder
public record InterviewProblemQuestionDto(
                                          String question,
                                          String answer,
                                          Integer sequence,
                                          InterviewProblemStatus status
) {

}
