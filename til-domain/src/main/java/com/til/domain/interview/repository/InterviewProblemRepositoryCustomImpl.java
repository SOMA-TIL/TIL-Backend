package com.til.domain.interview.repository;

import static com.til.domain.interview.model.QInterviewProblem.interviewProblem;
import static com.til.domain.problem.model.QProblem.problem;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InterviewProblemRepositoryCustomImpl implements InterviewProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<InterviewProblemQuestionDto> getInterviewProblemQuestionByInterviewId(Long interviewId) {
        return queryFactory.select(Projections.constructor(InterviewProblemQuestionDto.class,
            interviewProblem.id,
            interviewProblem.answer,
            interviewProblem.sequence,
            interviewProblem.status,
            interviewProblem.problemId,
            problem.question))
            .from(interviewProblem)
            .leftJoin(problem)
            .on(interviewProblem.problemId.eq(problem.id))
            .where(interviewProblem.interviewId.eq(interviewId))
            .fetch();
    }
}
