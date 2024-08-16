package com.til.domain.grading.repository;

import static com.til.domain.grading.model.QGrading.grading;
import static com.til.domain.problem.model.QProblem.problem;
import static com.til.domain.problem.model.QUserProblem.userProblem;

import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.common.exception.BaseException;
import com.til.domain.grading.dto.GradingInputDataDto;
import com.til.domain.grading.dto.GradingResultDto;
import com.til.domain.grading.enums.AnswerType;
import com.til.domain.problem.enums.ProblemErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GradingRepositoryCustomImpl implements GradingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public GradingInputDataDto getGradingInputDataFromUserProblem(Long targetId) {
        return queryFactory.select(Projections.constructor(GradingInputDataDto.class,
            problem.question,
            problem.grading,
            userProblem.answer
        ))
            .from(userProblem)
            .leftJoin(problem).on(userProblem.problemId.eq(problem.id))
            .where(userProblem.id.eq(targetId))
            .fetchOne();
    }

    @Override
    public GradingInputDataDto getGradingInputDataFromInterviewProblem(Long targetId) {
        return null;
    }

    @Override
    public GradingResultDto getResultFromUserProblem(Long userId, Long problemId, Long submitId) {
        return Optional.ofNullable(queryFactory.select(Projections.constructor(GradingResultDto.class,
            userProblem.status,
            grading.result,
            grading.comment
        ))
            .from(userProblem)
            .leftJoin(grading).on(userProblem.id.eq(grading.targetId), grading.type.eq(AnswerType.PROBLEM))
            .where(userProblem.id.eq(submitId), userProblem.problemId.eq(problemId), userProblem.userId.eq(userId))
            .fetchOne()
        ).orElseThrow(() -> new BaseException(ProblemErrorCode.NOT_FOUND_SUBMIT_HISTORY));
    }

    @Override
    public GradingResultDto getResultFromInterviewProblem(Long userId, Long interviewId, Long targetId) {
        return null;
    }
}
