package com.til.domain.problem.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.til.domain.category.model.QProblemCategory.problemCategory;
import static com.til.domain.problem.model.QProblem.problem;
import static com.til.domain.problem.model.QProblemStatistics.problemStatistics;
import static com.til.domain.problem.repository.ProblemQueryCondition.*;
import static com.til.utils.data.ListUtil.isNullOrEmpty;
import static com.til.utils.data.StringUtil.hasText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.*;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.enums.ProblemSortCriteria;
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

    @Override
    public Page<AdminProblemListDto> getProblemList(Pageable pageable,
        ProblemSearchDto searchDto) {
        BooleanExpression searchCondition = getSearchCondition(searchDto);
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable);

        List<ProblemBasicInfoDto> problemList = queryFactory
            .select(Projections.constructor(ProblemBasicInfoDto.class,
                problem.id,
                problem.title,
                problem.level,
                problemStatistics.passedCount.coalesce(0L),
                problemStatistics.passRate.coalesce(0F)
            ))
            .from(problem)
            .leftJoin(problemStatistics).on(linkProblemWithStatistics())
            .where(searchCondition)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .fetch();

        Map<Long, List<Long>> categoryInfo = queryFactory.from(problemCategory)
            .where(problemCategory.problemId.in(ProblemBasicInfoDto.getIdList(problemList)))
            .transform(
                groupBy(problemCategory.problemId)
                    .as(GroupBy.list(problemCategory.categoryId))
            );

        return new PageImpl<>(AdminProblemListDto.ofList(problemList, categoryInfo), pageable, getProblemCount(
            searchCondition));
    }

    @Override
    public AdminProblemInfoDto getProblemInfo(Long problemId) {
        return queryFactory.from(problem)
            .leftJoin(problemStatistics).on(linkProblemWithStatistics())
            .leftJoin(problemCategory).on(problem.id.eq(problemCategory.problemId))
            .where(problem.id.eq(problemId))
            .transform(
                groupBy(problem.id, problem.title, problem.question, problem.level)
                    .list(Projections.constructor(AdminProblemInfoDto.class,
                        problem.id,
                        problem.title,
                        problem.question,
                        problem.level,
                        problemStatistics.passedCount.coalesce(0L),
                        problemStatistics.passRate.coalesce(0F),
                        GroupBy.list(problemCategory.categoryId)
                    ))
            )
            .stream()
            .findFirst()
            .orElseThrow(() -> new BaseException(ProblemErrorCode.NOT_FOUND_PROBLEM));
    }

    private long getProblemCount(BooleanExpression searchCondition) {
        Long count = queryFactory.select(problem.countDistinct())
            .from(problem)
            .where(searchCondition)
            .fetchOne();
        return count == null ? 0 : count;
    }

    private BooleanExpression getSearchCondition(ProblemSearchDto searchDto) {
        return allOf(
            getKeywordCondition(searchDto.keyword()),
            getLevelCondition(searchDto.levelList()),
            getCategoryCondition(searchDto.categoryList())
        );
    }

    private BooleanExpression getKeywordCondition(String keyword) {
        return hasText(keyword) ? problem.title.containsIgnoreCase(keyword) : null;
    }

    private static BooleanExpression getLevelCondition(List<Integer> levelList) {
        return !isNullOrEmpty(levelList) ? problem.level.in(levelList) : null;
    }

    private BooleanExpression getCategoryCondition(List<Long> categoryList) {
        return !isNullOrEmpty(categoryList) ? JPAExpressions.selectFrom(problemCategory)
            .where(problemCategory.problemId.eq(problem.id))
            .where(problemCategory.categoryId.in(categoryList))
            .exists() : null;
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        Sort.Order order = pageable.getSort().iterator().next();
        String property = order.getProperty();

        ProblemSortCriteria sortCriteria = ProblemSortCriteria.fromString(property);

        PathBuilder<?> pathBuilder;
        if (sortCriteria == ProblemSortCriteria.ID || sortCriteria == ProblemSortCriteria.TITLE || sortCriteria
            == ProblemSortCriteria.LEVEL) {
            pathBuilder = new PathBuilder<>(problem.getType(), "problem");
        } else {
            pathBuilder = new PathBuilder<>(problemStatistics.getType(), "problemStatistics");
        }

        orderSpecifiers.add(new OrderSpecifier(
            order.isAscending() ? Order.ASC : Order.DESC,
            pathBuilder.get(property)
        ));

        if (sortCriteria != ProblemSortCriteria.ID) {
            PathBuilder<?> idPathBuilder = new PathBuilder<>(problem.getType(), "problem");
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, idPathBuilder.get(ProblemSortCriteria.ID
                .getFieldName())));
        }

        return orderSpecifiers;
    }
}
