package com.til.domain.problem.repository;

import java.util.List;

import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.problem.dto.SubmitHistoryDto;

public interface UserProblemRepositoryCustom {

    void updateStatus(Long id, GradingStatus status);

    List<SubmitHistoryDto> getSubmitHistory(Long userId, Long problemId);

    boolean isProblemPassed(Long userId, Long problemId);
}
