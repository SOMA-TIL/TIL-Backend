package com.til.controller.interview.response;

import java.util.List;

import com.til.domain.interview.dto.InterviewInfoDto;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;

public record InterviewInfoResponse(
                                    List<Long> categoryIdList,
                                    List<InterviewProblemQuestionDto> problemList) {

    public static InterviewInfoResponse of(InterviewInfoDto interviewInfoDto) {
        return new InterviewInfoResponse(interviewInfoDto.categoryIdList(), interviewInfoDto
            .problemList());
    }

}
