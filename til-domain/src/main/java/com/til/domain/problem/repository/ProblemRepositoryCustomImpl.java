package com.til.domain.problem.repository;

import static com.til.domain.problem.model.QProblem.problem;

import com.querydsl.jpa.impl.JPAQueryFactory;

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
}
