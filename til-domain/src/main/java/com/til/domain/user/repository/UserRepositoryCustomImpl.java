package com.til.domain.user.repository;

import static com.til.domain.user.model.QUser.user;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.common.exception.BaseException;
import com.til.domain.user.enums.UserErrorCode;
import com.til.domain.user.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByEmail(String email) {
        return queryFactory.selectOne()
            .from(user)
            .where(user.email.eq(email))
            .fetchFirst() != null;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return queryFactory.selectOne()
            .from(user)
            .where(user.nickname.eq(nickname))
            .fetchFirst() != null;
    }

    @Override
    public User getByEmail(String email) {
        return Optional.ofNullable(
            queryFactory.selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne()
        ).orElseThrow(() -> new BaseException(UserErrorCode.NOT_FOUND_USER));
    }
}