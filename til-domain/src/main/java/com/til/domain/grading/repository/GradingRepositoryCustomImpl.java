package com.til.domain.grading.repository;

import static com.til.domain.grading.model.QGrading.grading;
import static com.til.domain.problem.model.QProblem.problem;
import static com.til.domain.problem.model.QUserProblem.userProblem;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.grading.dto.GradingInputDataDto;
import com.til.domain.grading.dto.GradingResultDto;
import com.til.domain.grading.enums.AnswerType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GradingRepositoryCustomImpl implements GradingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public GradingInputDataDto getGradingInputDataFromUserProblem(Long targetId) {
        return queryFactory.select(Projections.constructor(GradingInputDataDto.class,
            problem.question,
            problem.solution,
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
    public GradingResultDto getResultByTypeAndTargetId(AnswerType answerType, Long targetId) {
        return queryFactory.select(Projections.constructor(GradingResultDto.class,
            grading.result,
            grading.comment
        ))
            .from(grading)
            .where(grading.type.eq(answerType), grading.targetId.eq(targetId))
            .fetchOne();
    }
}
