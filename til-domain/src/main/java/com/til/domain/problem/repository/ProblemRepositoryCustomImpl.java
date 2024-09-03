package com.til.domain.problem.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.til.domain.category.model.QProblemCategory.problemCategory;
import static com.til.domain.grading.model.QGrading.grading;
import static com.til.domain.problem.model.QFavoriteProblem.favoriteProblem;
import static com.til.domain.problem.model.QProblem.problem;
import static com.til.domain.problem.model.QProblemStatistics.problemStatistics;
import static com.til.domain.problem.model.QUserProblem.userProblem;
import static com.til.domain.problem.repository.ProblemQueryCondition.getProblemFavoriteCondition;
import static com.til.domain.problem.repository.ProblemQueryCondition.getProblemUserStatusCondition;
import static com.til.domain.problem.repository.ProblemQueryCondition.isGradingStatus;
import static com.til.domain.problem.repository.ProblemQueryCondition.isResultPassed;
import static com.til.domain.problem.repository.ProblemQueryCondition.linkProblemWithStatistics;
import static com.til.domain.problem.repository.ProblemQueryCondition.linkProblemWithUserFavorite;
import static com.til.domain.problem.repository.ProblemQueryCondition.linkProblemWithUserProblem;
import static com.til.domain.problem.repository.ProblemQueryCondition.linkUserProblemWithGrading;
import static com.til.utils.data.ListUtil.isNullOrEmpty;
import static com.til.utils.data.StringUtil.hasText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.common.exception.BaseException;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.problem.dto.ProblemBasicInfoDto;
import com.til.domain.problem.dto.ProblemOverviewInfoDto;
import com.til.domain.problem.dto.ProblemPublicInfoDto;
import com.til.domain.problem.dto.ProblemSearchDto;
import com.til.domain.problem.dto.ProblemUserStatusDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.enums.ProblemSortCriteria;
import com.til.domain.problem.enums.ProblemUserStatus;

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
    public Page<ProblemOverviewInfoDto> getProblemPublicOverviewInfoList(Pageable pageable,
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

        return new PageImpl<>(ProblemOverviewInfoDto.ofList(problemList, categoryInfo), pageable, getProblemCount(
            searchCondition));
    }

    @Override
    public Page<ProblemOverviewInfoDto> getProblemOverviewListWithUserData(Pageable pageable,
        ProblemSearchDto searchDto, Long userId) {
        BooleanExpression searchCondition = getSearchCondition(searchDto);
        BooleanExpression userStatusCondition = getUserStatusCondition(searchDto.status(), searchDto.isFavorite());
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable);

        List<Tuple> data = queryFactory
            .select(
                Projections.constructor(ProblemBasicInfoDto.class,
                    problem.id,
                    problem.title,
                    problem.level,
                    problemStatistics.passedCount.coalesce(0L),
                    problemStatistics.passRate.coalesce(0F)
                ),
                Projections.constructor(ProblemUserStatusDto.class,
                    favoriteProblem.id.count().gt(0),
                    userProblem.id.count().gt(0),
                    grading.result.count().gt(0)
                )
            )
            .from(problem)
            .leftJoin(problemStatistics).on(linkProblemWithStatistics())
            .leftJoin(userProblem).on(linkProblemWithUserProblem(userId), isGradingStatus(GradingStatus.COMPLETED))
            .leftJoin(grading).on(linkUserProblemWithGrading(), isResultPassed())
            .leftJoin(favoriteProblem).on(linkProblemWithUserFavorite(userId))
            .where(searchCondition)
            .groupBy(problem.id, problem.title, problem.level)
            .having(userStatusCondition)
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<Long> problemIdList = data.stream()
            .map(tuple -> Objects.requireNonNull(tuple.get(0, ProblemBasicInfoDto.class)).id())
            .toList();

        Map<Long, List<Long>> categoryInfo = queryFactory.from(problemCategory)
            .where(problemCategory.problemId.in(problemIdList))
            .transform(
                groupBy(problemCategory.problemId)
                    .as(GroupBy.list(problemCategory.categoryId))
            );

        return new PageImpl<>(ProblemOverviewInfoDto.ofListFromTuple(data, categoryInfo), pageable,
            getProblemCount(userId, searchCondition, userStatusCondition));
    }

    @Override
    public ProblemPublicInfoDto getProblemPublicInfo(Long problemId) {
        return queryFactory.from(problem)
            .leftJoin(problemStatistics).on(linkProblemWithStatistics())
            .leftJoin(problemCategory).on(problem.id.eq(problemCategory.problemId))
            .where(problem.id.eq(problemId))
            .transform(
                groupBy(problem.id, problem.title, problem.question, problem.level)
                    .list(Projections.constructor(ProblemPublicInfoDto.class,
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

    private long getProblemCount(Long userId, BooleanExpression searchCondition,
        BooleanExpression userStatusCondition) {
        JPQLQuery<Long> subQuery = queryFactory.select(problem.id)
            .from(problem)
            .leftJoin(problemStatistics).on(linkProblemWithStatistics())
            .leftJoin(userProblem).on(linkProblemWithUserProblem(userId), isGradingStatus(GradingStatus.COMPLETED))
            .leftJoin(grading).on(linkUserProblemWithGrading(), isResultPassed())
            .leftJoin(favoriteProblem).on(linkProblemWithUserFavorite(userId))
            .where(searchCondition)
            .groupBy(problem.id)
            .having(userStatusCondition);

        return subQuery.fetch().size();
    }

    private BooleanExpression getSearchCondition(ProblemSearchDto searchDto) {
        return allOf(
            getKeywordCondition(searchDto.keyword()),
            getLevelCondition(searchDto.levelList()),
            getCategoryCondition(searchDto.categoryList())
        );
    }

    private BooleanExpression getUserStatusCondition(ProblemUserStatus userStatus, boolean isFavorite) {
        return allOf(
            getProblemUserStatusCondition(userStatus),
            getProblemFavoriteCondition(isFavorite)
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
