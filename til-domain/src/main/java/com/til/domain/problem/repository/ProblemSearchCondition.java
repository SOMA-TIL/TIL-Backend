package com.til.domain.problem.repository;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.til.common.utils.data.ListUtil.isNullOrEmpty;
import static com.til.common.utils.data.StringUtil.hasText;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.til.domain.category.model.QProblemCategory;
import com.til.domain.problem.dto.ProblemSearchDto;
import com.til.domain.problem.model.QProblem;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProblemSearchCondition {

    private QProblem problem;
    private QProblemCategory problemCategory;

    public ProblemSearchCondition(QProblem problem, QProblemCategory problemCategory) {
        this.problem = problem;
        this.problemCategory = problemCategory;
    }

    public BooleanExpression getSearchCondition(ProblemSearchDto searchDto) {
        return allOf(
            getKeywordCondition(searchDto.keyword()),
            getLevelCondition(searchDto.levelList()),
            getCategoryCondition(searchDto.categoryList())
        );
    }

    private BooleanExpression getKeywordCondition(String keyword) {
        return hasText(keyword) ? problem.title.containsIgnoreCase(keyword) : null;
    }

    private BooleanExpression getLevelCondition(List<Integer> levelList) {
        return !isNullOrEmpty(levelList) ? problem.level.in(levelList) : null;
    }

    private BooleanExpression getCategoryCondition(List<Long> categoryList) {
        return !isNullOrEmpty(categoryList) ? JPAExpressions.selectFrom(problemCategory)
            .where(problemCategory.problemId.eq(problem.id))
            .where(problemCategory.categoryId.in(categoryList))
            .exists() : null;
    }
}
