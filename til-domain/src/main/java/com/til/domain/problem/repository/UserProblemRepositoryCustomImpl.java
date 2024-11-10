package com.til.domain.problem.repository;

import static com.til.domain.grading.model.QGrading.grading;
import static com.til.domain.problem.model.QUserProblem.userProblem;
import static com.til.domain.user.model.QUser.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.grading.enums.AnswerType;
import com.til.domain.grading.enums.GradingResult;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.problem.dto.OthersAnswerDto;
import com.til.domain.problem.dto.SubmitHistoryDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserProblemRepositoryCustomImpl implements UserProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public void updateStatus(Long id, GradingStatus status) {
        queryFactory.update(userProblem)
            .set(userProblem.status, status)
            .where(userProblem.id.eq(id))
            .execute();
    }

    @Override
    public List<SubmitHistoryDto> getSubmitHistory(Long userId, Long problemId) {
        return queryFactory.select(Projections.constructor(
            SubmitHistoryDto.class,
            userProblem.id,
            userProblem.answer,
            grading.result,
            grading.comment,
            userProblem.createdDate
        ))
            .from(userProblem)
            .leftJoin(grading)
            .on(userProblem.id.eq(grading.targetId))
            .where(getUserProblemConditions(userId, problemId))
            .orderBy(userProblem.createdDate.desc(), userProblem.id.desc())
            .fetch();
    }

    @Override
    public boolean isProblemPassed(Long userId, Long problemId) {
        return queryFactory.selectOne()
            .from(userProblem)
            .leftJoin(grading)
            .on(userProblem.id.eq(grading.targetId))
            .where(
                getUserProblemConditions(userId, problemId)
                    .and(grading.result.eq(GradingResult.PASS))
            )
            .fetchFirst() != null;
    }

    private BooleanExpression getUserProblemConditions(Long userId, Long problemId) {
        return userProblem.userId.eq(userId)
            .and(grading.type.eq(AnswerType.PROBLEM))
            .and(userProblem.problemId.eq(problemId));
    }

    @Override
    public Page<OthersAnswerDto> getPassedOthersAnswer(Long userId, Long problemId, Pageable pageable) {
        List<OthersAnswerDto> content = queryFactory
            .select(Projections.constructor(
                OthersAnswerDto.class,
                userProblem.id,
                userProblem.problemId,
                user.nickname,
                userProblem.answer
            ))
            .from(userProblem)
            .leftJoin(grading)
            .on(userProblem.id.eq(grading.targetId)
                .and(grading.userId.eq(userProblem.userId)))
            .leftJoin(user).on(userProblem.userId.eq(user.id))
            .where(
                userProblem.problemId.eq(problemId),
                userProblem.userId.ne(userId),
                grading.type.eq(AnswerType.PROBLEM),
                grading.result.eq(GradingResult.PASS)
            )
            .orderBy(userProblem.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        var countQuery = queryFactory
            .select(userProblem.count())
            .from(userProblem)
            .leftJoin(grading)
            .on(userProblem.id.eq(grading.targetId)
                .and(grading.userId.eq(userProblem.userId)))
            .leftJoin(user).on(userProblem.userId.eq(user.id))
            .where(
                userProblem.problemId.eq(problemId),
                userProblem.userId.ne(userId),
                grading.type.eq(AnswerType.PROBLEM),
                grading.result.eq(GradingResult.PASS)
            );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
