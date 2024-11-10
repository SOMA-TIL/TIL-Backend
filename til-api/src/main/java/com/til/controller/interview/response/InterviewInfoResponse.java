package com.til.controller.interview.response;

import java.time.LocalDateTime;
import java.util.List;

import com.til.domain.interview.dto.InterviewInfoDto;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;
import com.til.domain.interview.model.InterviewType;

public record InterviewInfoResponse(
                                    InterviewType type,
                                    LocalDateTime createdDate,
                                    List<Long> categoryIdList,
                                    List<InterviewProblemQuestionDto> problemList) {

    public static InterviewInfoResponse of(InterviewInfoDto interviewInfoDto) {
        return new InterviewInfoResponse(interviewInfoDto.type(), interviewInfoDto.createdDate(),
            interviewInfoDto.categoryIdList(),
            interviewInfoDto.problemList());
    }

}
