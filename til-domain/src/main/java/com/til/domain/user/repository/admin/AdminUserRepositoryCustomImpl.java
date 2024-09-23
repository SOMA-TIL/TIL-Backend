package com.til.domain.user.repository.admin;

import static com.til.domain.user.model.QUser.user;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.common.exception.BaseException;
import com.til.domain.user.enums.UserErrorCode;
import com.til.domain.user.model.Role;
import com.til.domain.user.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminUserRepositoryCustomImpl implements AdminUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public User getByEmail(String email) {
        return Optional.ofNullable(
            queryFactory.selectFrom(user)
                .where(user.email.eq(email), user.role.eq(Role.ADMIN))
                .fetchOne()
        ).orElseThrow(() -> new BaseException(UserErrorCode.NOT_FOUND_USER));
    }
}
