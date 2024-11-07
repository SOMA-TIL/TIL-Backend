package com.til.domain.category.repository;

import static com.til.domain.category.model.QProblemCategory.problemCategory;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.category.model.ProblemCategory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProblemCategoryRepositoryCustomImpl implements ProblemCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Long> getProblemIdListByCategoryId(Long categoryId, int questionSize) {
        return queryFactory.select(problemCategory.problemId)
            .from(problemCategory)
            .where(problemCategory.categoryId.eq(categoryId))
            .limit(questionSize)
            .fetch();
    }

    @Override
    @Transactional
    public void saveAllProblemCategories(Long problemId, List<Long> categoryIdList) {
        for (Long categoryId : categoryIdList) {
            ProblemCategory problemCategory = ProblemCategory.builder()
                .problemId(problemId)
                .categoryId(categoryId)
                .build();
            entityManager.persist(problemCategory);
        }
        entityManager.flush();
        entityManager.clear();
    }
}
