package com.til.domain.problem.repository;

import static com.til.domain.grading.model.QGrading.grading;
import static com.til.domain.problem.model.QFavoriteProblem.favoriteProblem;
import static com.til.domain.problem.model.QProblem.problem;
import static com.til.domain.problem.model.QProblemStatistics.problemStatistics;
import static com.til.domain.problem.model.QUserProblem.userProblem;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.til.domain.grading.enums.AnswerType;
import com.til.domain.grading.enums.GradingResult;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.problem.enums.ProblemUserStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProblemQueryCondition {

    public static BooleanExpression linkProblemWithUserProblem(Long userId) {
        return problem.id.eq(userProblem.problemId)
            .and(userProblem.userId.eq(userId));
    }

    public static BooleanExpression linkUserProblemWithGrading() {
        return userProblem.id.eq(grading.targetId)
            .and(grading.type.eq(AnswerType.PROBLEM));
    }

    public static BooleanExpression linkProblemWithUserFavorite(Long userId) {
        return problem.id.eq(favoriteProblem.problemId)
            .and(favoriteProblem.userId.eq(userId));
    }

    public static BooleanExpression linkProblemWithStatistics() {
        return problem.id.eq(problemStatistics.problemId);
    }

    public static BooleanExpression isGradingStatus(GradingStatus status) {
        return userProblem.status.eq(status);
    }

    public static BooleanExpression isResultPassed() {
        return grading.result.eq(GradingResult.PASS);
    }

    public static BooleanExpression getProblemUserStatusCondition(ProblemUserStatus userStatus) {
        return (userStatus == null) ? null : switch (userStatus) {
            case PASS -> userProblem.id.count().gt(0).and(grading.result.count().gt(0L));
            case FAIL -> userProblem.id.count().gt(0).and(grading.result.count().eq(0L));
            case NOT_ATTEMPTED -> userProblem.id.count().eq(0L);
        };
    }

    public static BooleanExpression getProblemFavoriteCondition(boolean isFavorite) {
        return isFavorite ? favoriteProblem.id.count().gt(0) : null;
    }
}
