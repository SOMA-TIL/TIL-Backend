package com.til.controller.interview.response;

import java.util.List;

import com.til.domain.category.dto.CategoryDto;
import com.til.domain.interview.dto.InterviewInfoDto;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;

public record InterviewInfoResponse(
                                    Long id,
                                    List<CategoryDto> categoryList,
                                    List<InterviewProblemQuestionDto> problemList) {

    public static InterviewInfoResponse of(InterviewInfoDto interviewInfoDto) {
        return new InterviewInfoResponse(interviewInfoDto.id(), interviewInfoDto.categoryList(), interviewInfoDto
            .problemList());
    }

}
