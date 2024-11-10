package com.til.domain.problem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.problem.dto.OthersAnswerDto;
import com.til.domain.problem.dto.SubmitHistoryDto;

public interface UserProblemRepositoryCustom {

    void updateStatus(Long id, GradingStatus status);

    List<SubmitHistoryDto> getSubmitHistory(Long userId, Long problemId);

    boolean isProblemPassed(Long userId, Long problemId);

    Page<OthersAnswerDto> getPassedOthersAnswer(Long userId, Long problemId, Pageable pageable);
}
