package com.til.domain.category.repository;

import static com.til.domain.category.model.QProblemCategory.problemCategory;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.category.model.ProblemCategory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminProblemCategoryRepositoryCustomImpl implements AdminProblemCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void deleteByProblemId(Long problemId) {
        queryFactory.delete(problemCategory)
            .where(problemCategory.problemId.eq(problemId))
            .execute();
    }

    @Override
    @Transactional
    public void updateProblemCategories(Long problemId, List<Long> categoryIdList) {
        deleteByProblemId(problemId);

        categoryIdList.forEach(categoryId -> {
            ProblemCategory newProblemCategory = ProblemCategory.createProblemCategory(problemId, categoryId);
            entityManager.persist(newProblemCategory);
        });
    }
}
