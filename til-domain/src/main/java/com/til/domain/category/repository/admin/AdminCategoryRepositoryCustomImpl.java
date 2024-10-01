package com.til.domain.category.repository.admin;

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

        Long validCount = queryFactory.select(category.count())
            .from(category)
            .where(category.id.in(categoryIdList))
            .fetchFirst();

        if (validCount == null || validCount != categoryIdList.size()) {
            throw new BaseException(CategoryErrorCode.NOT_FOUND_CATEGORY);
        }
    }
}
