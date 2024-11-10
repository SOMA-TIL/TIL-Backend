package com.til.domain.interview.repository;

import static com.til.domain.interview.model.QExperienceInterviewProblem.experienceInterviewProblem;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;
import com.til.domain.interview.model.InterviewProblemStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExperienceInterviewProblemRepositoryCustomImpl implements ExperienceInterviewProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<InterviewProblemQuestionDto> getExperienceInterviewProblemQuestionByInterviewId(Long interviewId) {
        return queryFactory.select(Projections.constructor(InterviewProblemQuestionDto.class,
            experienceInterviewProblem.answer,
            experienceInterviewProblem.sequence,
            experienceInterviewProblem.status,
            experienceInterviewProblem.question))
            .from(experienceInterviewProblem)
            .where(experienceInterviewProblem.interviewId.eq(interviewId))
            .fetch();
    }

    @Override
    public boolean existsBySolvable(Long interviewId, Integer sequence, InterviewProblemStatus status) {
        return queryFactory.selectOne()
            .from(experienceInterviewProblem)
            .where(experienceInterviewProblem.interviewId.eq(interviewId), experienceInterviewProblem.sequence.eq(
                sequence),
                experienceInterviewProblem.status.eq(status))
            .fetchOne() != null;
    }

    @Override
    public boolean existsBySequenceConsistency(Long interviewId, Integer sequence,
        InterviewProblemStatus status) {
        return queryFactory.selectOne()
            .from(experienceInterviewProblem)
            .where(experienceInterviewProblem.interviewId.eq(interviewId), experienceInterviewProblem.sequence.lt(
                sequence),
                experienceInterviewProblem.status.eq(status))
            .fetchFirst() != null;
    }

    @Override
    public void solveInterviewProblem(Long interviewId, Integer sequence, String answer) {
        queryFactory.update(experienceInterviewProblem)
            .set(experienceInterviewProblem.status, InterviewProblemStatus.SOLVED)
            .set(experienceInterviewProblem.answer, answer)
            .where(experienceInterviewProblem.interviewId.eq(interviewId), experienceInterviewProblem.sequence.eq(
                sequence))
            .execute();
    }
}
