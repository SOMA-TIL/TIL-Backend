package com.til.controller.interview.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.til.domain.grading.dto.GradingResultWithProblemInfoDto;
import com.til.domain.grading.dto.InterviewGradingResultDto;
import com.til.domain.interview.model.InterviewStatus;

public record InterviewResultResponse(
                                      InterviewStatus interviewStatus,
                                      @JsonInclude(Include.NON_NULL) List<GradingResultWithProblemInfoDto> gradingResult
) {

    public static InterviewResultResponse of(InterviewGradingResultDto gradingResultDto) {
        return new InterviewResultResponse(gradingResultDto.status(), gradingResultDto.gradingResult());
    }

}
