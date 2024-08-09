package com.til.domain.grading.repository;

import com.til.domain.grading.dto.GradingInputDataDto;
import com.til.domain.grading.dto.GradingResultDto;
import com.til.domain.grading.enums.AnswerType;

public interface GradingRepositoryCustom {

    GradingInputDataDto getGradingInputDataFromUserProblem(Long targetId);

    GradingInputDataDto getGradingInputDataFromInterviewProblem(Long targetId);

    GradingResultDto getResultByTypeAndTargetId(AnswerType type, Long targetId);
}
