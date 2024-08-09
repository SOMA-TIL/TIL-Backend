package com.til.domain.problem.repository;

import static com.til.domain.problem.model.QUserProblem.userProblem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.grading.enums.GradingStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserProblemRepositoryCustomImpl implements UserProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public void updateStatus(Long id, GradingStatus status) {
        queryFactory.update(userProblem)
            .set(userProblem.status, status)
            .where(userProblem.id.eq(id))
            .execute();
    }
}
