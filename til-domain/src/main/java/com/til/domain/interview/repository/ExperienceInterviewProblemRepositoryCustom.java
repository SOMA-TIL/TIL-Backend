package com.til.domain.interview.repository;

import java.util.List;

import com.til.domain.interview.dto.InterviewProblemQuestionDto;
import com.til.domain.interview.model.InterviewProblemStatus;

public interface ExperienceInterviewProblemRepositoryCustom {

    List<InterviewProblemQuestionDto> getExperienceInterviewProblemQuestionByInterviewId(Long interviewId);

    boolean existsBySolvable(Long interviewId, Integer sequence, InterviewProblemStatus interviewProblemStatus);

    boolean existsBySequenceConsistency(Long interviewId, Integer sequence,
        InterviewProblemStatus interviewProblemStatus);

    void solveInterviewProblem(Long interviewId, Integer sequence, String answer);
}
