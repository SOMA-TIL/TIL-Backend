package com.til.domain.interview.repository;

import static com.til.domain.interview.model.QInterviewProblem.interviewProblem;
import static com.til.domain.problem.model.QProblem.problem;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;
import com.til.domain.interview.model.InterviewProblemStatus;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InterviewProblemRepositoryCustomImpl implements InterviewProblemRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<InterviewProblemQuestionDto> getInterviewProblemQuestionByInterviewId(Long interviewId) {
        return queryFactory.select(Projections.constructor(InterviewProblemQuestionDto.class,
            interviewProblem.answer,
            interviewProblem.sequence,
            interviewProblem.status,
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

    @Override
    public boolean existsByInterviewIdAndStatus(Long interviewId, InterviewProblemStatus status) {
        return queryFactory.selectOne()
            .from(interviewProblem)
            .where(interviewProblem.interviewId.eq(interviewId), interviewProblem.status.eq(status))
            .fetchFirst() != null;
    }

    @Override
    @Transactional
    public void updateProblemGradingStatusById(Long id, GradingStatus gradingStatus) {
        queryFactory.update(interviewProblem)
            .set(interviewProblem.gradingStatus, gradingStatus)
            .where(interviewProblem.id.eq(id))
            .execute();
    }

    @Override
    public void updateProblemGradingStatusByInterviewId(Long interviewId, GradingStatus gradingStatus) {
        queryFactory.update(interviewProblem)
            .set(interviewProblem.gradingStatus, gradingStatus)
            .where(interviewProblem.interviewId.eq(interviewId))
            .execute();

        entityManager.flush();
        entityManager.clear();
    }

}
