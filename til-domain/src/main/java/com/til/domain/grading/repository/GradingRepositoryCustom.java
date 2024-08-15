package com.til.domain.grading.repository;

import com.til.domain.grading.dto.GradingInputDataDto;
import com.til.domain.grading.dto.GradingResultDto;

public interface GradingRepositoryCustom {

    GradingInputDataDto getGradingInputDataFromUserProblem(Long targetId);

    GradingInputDataDto getGradingInputDataFromInterviewProblem(Long targetId);

    GradingResultDto getResultFromUserProblem(Long userId, Long problemId, Long submitId);

    GradingResultDto getResultFromInterviewProblem(Long userId, Long interviewId, Long submitId);
}
