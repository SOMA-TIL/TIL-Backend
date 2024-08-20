package com.til.domain.interview.repository;

import static com.til.domain.interview.model.QInterviewProblem.interviewProblem;
import static com.til.domain.problem.model.QProblem.problem;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;
import com.til.domain.interview.model.InterviewProblemStatus;

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

    @Override
    public boolean existsBySolvable(Long interviewId, Integer sequence, InterviewProblemStatus status) {
        return queryFactory.selectOne()
            .from(interviewProblem)
            .where(interviewProblem.interviewId.eq(interviewId), interviewProblem.sequence.eq(sequence),
                interviewProblem.status.eq(
                    status))
            .fetchFirst() != null;
    }

    @Override
    public boolean existsBySequenceConsistency(Long interviewId, Integer sequence, InterviewProblemStatus status) {
        return queryFactory.selectOne()
            .from(interviewProblem)
            .where(interviewProblem.interviewId.eq(interviewId), interviewProblem.sequence.lt(sequence),
                interviewProblem.status.eq(status))
            .fetchFirst() != null;
    }

    @Override
    public void solveInterviewProblem(Long interviewId, Integer sequence, String answer) {
        queryFactory.update(interviewProblem)
            .set(interviewProblem.status, InterviewProblemStatus.SOLVED)
            .set(interviewProblem.answer, answer)
            .where(interviewProblem.interviewId.eq(interviewId), interviewProblem.sequence.eq(sequence))
            .execute();
    }
}
