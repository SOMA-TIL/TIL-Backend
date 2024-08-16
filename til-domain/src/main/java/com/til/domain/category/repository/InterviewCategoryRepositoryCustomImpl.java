package com.til.domain.category.repository;

import static com.til.domain.category.model.QCategory.category;
import static com.til.domain.category.model.QInterviewCategory.interviewCategory;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InterviewCategoryRepositoryCustomImpl implements InterviewCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> getCategoryIdListByInterviewId(Long interviewId) {
        return queryFactory.select(category.id)
            .from(interviewCategory)
            .leftJoin(category)
            .on(interviewCategory.categoryId.eq(category.id))
            .where(interviewCategory.interviewId.eq(interviewId))
            .fetch();
    }
}
