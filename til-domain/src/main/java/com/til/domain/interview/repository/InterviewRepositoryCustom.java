package com.til.domain.interview.repository;

import com.til.domain.interview.model.Interview;
import com.til.domain.interview.model.InterviewStatus;

public interface InterviewRepositoryCustom {

    boolean existsByCode(String code);

    boolean existsByUserIdAndStatus(Long userId, InterviewStatus status);

    Interview getProcessingInterview(Long userId, String code);

    void updateInterviewStatus(Long id, InterviewStatus status);
}
