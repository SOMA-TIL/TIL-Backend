package com.til.domain.category.repository;

import static com.til.domain.category.model.QProblemCategory.problemCategory;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProblemCategoryRepositoryCustomImpl implements ProblemCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> getProblemIdListByCategoryId(Long categoryId) {
        return queryFactory.select(problemCategory.problemId)
            .from(problemCategory)
            .where(problemCategory.categoryId.eq(categoryId))
            .limit(10) // todo: 우선 문제 최대 10개까지만 가져오도록 해둠, 추후 리팩토링
            .fetch();
    }
}
