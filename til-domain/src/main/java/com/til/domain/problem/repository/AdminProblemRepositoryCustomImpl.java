package com.til.domain.problem.repository;

import static com.til.domain.problem.model.QProblem.problem;
import static com.til.domain.problem.repository.ProblemQueryCondition.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.til.domain.problem.dto.*;
import com.til.domain.problem.model.QProblem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminProblemRepositoryCustomImpl implements AdminProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public void updateProblem(AdminUpdateProblemDto adminUpdateProblemDto) {
        QProblem problem = QProblem.problem;

        JPAUpdateClause updateClause = queryFactory.update(problem)
            .where(problem.id.eq(adminUpdateProblemDto.id()));

        boolean hasUpdate = false;

        if (adminUpdateProblemDto.title() != null ||
            adminUpdateProblemDto.question() != null ||
            adminUpdateProblemDto.solution() != null ||
            adminUpdateProblemDto.grading() != null ||
            adminUpdateProblemDto.level() != null) {

            if (adminUpdateProblemDto.title() != null) {
                updateClause.set(problem.title, adminUpdateProblemDto.title());
                hasUpdate = true;
            }
            if (adminUpdateProblemDto.question() != null) {
                updateClause.set(problem.question, adminUpdateProblemDto.question());
                hasUpdate = true;
            }
            if (adminUpdateProblemDto.solution() != null) {
                updateClause.set(problem.solution, adminUpdateProblemDto.solution());
                hasUpdate = true;
            }
            if (adminUpdateProblemDto.grading() != null) {
                updateClause.set(problem.grading, adminUpdateProblemDto.grading());
                hasUpdate = true;
            }
            if (adminUpdateProblemDto.level() != null) {
                updateClause.set(problem.level, adminUpdateProblemDto.level());
                hasUpdate = true;
            }
        }

        if (hasUpdate) {
            updateClause.execute();
        }
    }
}
