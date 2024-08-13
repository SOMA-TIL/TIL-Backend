package com.til.domain.problem.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.til.domain.category.model.QProblemCategory.problemCategory;
import static com.til.domain.problem.model.QProblem.problem;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.ProblemOverviewInfoDto;
import com.til.domain.problem.dto.ProblemPublicInfoDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.QProblem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProblemRepositoryCustomImpl implements ProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public String getSolutionByProblemId(Long problemId) {
        return queryFactory.select(problem.solution)
            .from(problem)
            .where(problem.id.eq(problemId))
            .fetchOne();
    }

    @Override
    public Page<ProblemOverviewInfoDto> getProblemOverviewInfoList(Pageable pageable) {
        List<ProblemOverviewInfoDto> content = queryFactory.from(problem)
            .leftJoin(problemCategory).on(problem.id.eq(problemCategory.problemId))
            .where(getPageConditions(pageable))
            .orderBy(problem.id.desc())
            .transform(
                groupBy(problem.id, problem.title, problem.level)
                    .list(Projections.constructor(ProblemOverviewInfoDto.class,
                        problem.id,
                        problem.title,
                        problem.level,
                        GroupBy.list(problemCategory.categoryId)
                    ))
            );

        Long total = queryFactory.select(problem.count())
            .from(problem)
            .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    @Override
    public ProblemPublicInfoDto getProblemPublicInfo(Long problemId) {
        return queryFactory.from(problem)
            .leftJoin(problemCategory).on(problem.id.eq(problemCategory.problemId))
            .where(problem.id.eq(problemId))
            .transform(
                groupBy(problem.id, problem.title, problem.question, problem.level)
                    .list(Projections.constructor(ProblemPublicInfoDto.class,
                        problem.id,
                        problem.title,
                        problem.question,
                        problem.level,
                        GroupBy.list(problemCategory.categoryId)
                    ))
            )
            .stream()
            .findFirst()
            .orElseThrow(() -> new BaseException(ProblemErrorCode.NOT_FOUND_PROBLEM));
    }

    private BooleanExpression getPageConditions(Pageable pageable) {
        QProblem subProblem = new QProblem("subProblem");
        return problem.id.in(
            queryFactory.from(subProblem)
                .select(subProblem.id)
                .from(subProblem)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(subProblem.id.desc()) // TODO : pageable.getSort()
                .fetch()
        );
    }
}
