package com.til.domain.interview.repository;

import static com.til.domain.interview.model.QInterview.interview;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.interview.model.InterviewStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InterviewRepositoryCustomImpl implements InterviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByCode(String code) {
        return queryFactory.selectOne()
            .from(interview)
            .where(interview.code.eq(code))
            .fetchFirst() != null;
    }

    @Override
    public boolean existsByUserIdAndStatus(Long userId, InterviewStatus status) {
        return queryFactory.selectOne()
            .from(interview)
            .where(interview.userId.eq(userId), interview.status.eq(status))
            .fetchFirst() != null;
    }

}
