package com.til.domain.interview.repository;

import java.util.List;

import com.til.domain.interview.dto.InterviewProblemQuestionDto;

public interface InterviewProblemRepositoryCustom {

    List<InterviewProblemQuestionDto> getInterviewProblemQuestionByInterviewId(Long interviewId);

}
