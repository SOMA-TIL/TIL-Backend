package com.til.domain.interview.repository;

import java.util.List;

import com.til.domain.interview.dto.InterviewProblemQuestionDto;
import com.til.domain.interview.model.InterviewProblemStatus;

public interface InterviewProblemRepositoryCustom {

    List<InterviewProblemQuestionDto> getInterviewProblemQuestionByInterviewId(Long interviewId);

    boolean existsBySolvable(Long interviewId, Integer sequence, InterviewProblemStatus status);

    boolean existsBySequenceConsistency(Long interviewId, Integer sequence, InterviewProblemStatus status);

    void solveInterviewProblem(Long interviewId, Integer sequence, String answer);
}
