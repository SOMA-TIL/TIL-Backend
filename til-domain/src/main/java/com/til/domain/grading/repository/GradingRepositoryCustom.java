package com.til.domain.grading.repository;

import java.util.Map;

import com.til.domain.grading.dto.GradingInputDataDto;
import com.til.domain.grading.dto.GradingResultDto;
import com.til.domain.grading.dto.InterviewGradingResultDto;

public interface GradingRepositoryCustom {

    GradingInputDataDto getGradingInputDataFromUserProblem(Long targetId);

    Map<Long, GradingInputDataDto> getGradingInputDataFromInterview(Long interviewId);

    GradingResultDto getResultFromUserProblem(Long userId, Long problemId, Long submitId);

    InterviewGradingResultDto getResultFromInterview(Long userId, Long interviewId);
}
