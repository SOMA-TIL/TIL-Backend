package com.til.domain.category.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.common.exception.BaseException;
import com.til.domain.category.enums.CategoryErrorCode;
import com.til.domain.category.model.QCategory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminCategoryRepositoryCustomImpl implements AdminCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void validateCategoryIds(List<Long> categoryIdList) {
        QCategory category = QCategory.category;

        long validCount = queryFactory.select(category.id)
            .from(category)
            .where(category.id.in(categoryIdList))
            .fetchCount();

        if (validCount != categoryIdList.size()) {
            throw new BaseException(CategoryErrorCode.NOT_FOUND_CATEGORY);
        }
    }
}
