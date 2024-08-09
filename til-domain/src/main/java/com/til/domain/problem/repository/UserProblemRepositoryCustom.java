package com.til.domain.problem.repository;

import com.til.domain.grading.enums.GradingStatus;

public interface UserProblemRepositoryCustom {

    void updateStatus(Long id, GradingStatus status);
}
