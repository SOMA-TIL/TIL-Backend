package com.til.domain.interview.repository;

import static com.til.domain.interview.model.QInterview.interview;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.common.exception.BaseException;
import com.til.domain.interview.enums.InterviewErrorCode;
import com.til.domain.interview.model.Interview;
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

    @Override
    public Interview getProcessingInterview(Long userId, String code) {
        return Optional.ofNullable(
            queryFactory.selectFrom(interview)
                .where(interview.code.eq(code), interview.userId.eq(userId), interview.status.eq(
                    InterviewStatus.PROCESSING))
                .fetchOne()
        ).orElseThrow(() -> new BaseException(InterviewErrorCode.NOT_FOUND_INTERVIEW));
    }

    @Override
    public Long getIdByUserIdAndCode(Long userId, String code) {
        return Optional.ofNullable(queryFactory.select(interview.id)
            .from(interview)
            .where(interview.userId.eq(userId), interview.code.eq(code))
            .fetchOne()
        ).orElseThrow(() -> new BaseException(InterviewErrorCode.NOT_FOUND_INTERVIEW));
    }

    @Override
    @Transactional
    public void updateInterviewStatus(Long id, InterviewStatus status) {
        queryFactory.update(interview)
            .set(interview.status, status)
            .where(interview.id.eq(id))
            .execute();

    }
}
