package com.til.config.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.til.domain.history.annotation.LogProblemViewHistory;
import com.til.domain.history.enums.ActionType;
import com.til.domain.history.model.Histories;
import com.til.domain.history.repository.HistoriesRepository;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class HistoryLogAspect {

    private final HistoriesRepository historiesRepository;

    @AfterReturning("@annotation(logHistory)")
    public void saveHistoryLog(JoinPoint joinPoint, LogProblemViewHistory logHistory) {
        Long userId = getUserId(joinPoint);
        Long problemId = getProblemId(joinPoint);
        Histories history = Histories.builder()
            .action(ActionType.VIEW)
            .userId(userId)
            .target("PROBLEM_INFO")
            .targetId(problemId)
            .build();

        historiesRepository.save(history);
    }

    private Long getUserId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Long) {
            return (Long) args[0];
        }
        return null;
    }

    private Long getProblemId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 1 && args[1] instanceof Long) {
            return (Long) args[1];
        }
        return null;
    }
}
