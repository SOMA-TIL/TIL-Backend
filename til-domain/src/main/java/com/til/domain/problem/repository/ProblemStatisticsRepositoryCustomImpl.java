package com.til.domain.problem.repository;

import static com.til.domain.problem.model.QProblemHourlyViewStatistics.problemHourlyViewStatistics;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.history.dto.TargetCountSummaryDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProblemStatisticsRepositoryCustomImpl implements ProblemStatisticsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void saveHourlyViewStatistics(LocalDateTime targetTime, List<TargetCountSummaryDto> viewSummary) {
        viewSummary.forEach(
            summary -> queryFactory
                .insert(problemHourlyViewStatistics)
                .columns(problemHourlyViewStatistics.statisticDate,
                    problemHourlyViewStatistics.statisticHour,
                    problemHourlyViewStatistics.problemId,
                    problemHourlyViewStatistics.viewCount)
                .values(
                    targetTime.toLocalDate(),
                    targetTime.getHour(),
                    summary.targetId(),
                    summary.count()
                )
                .execute()
        );
    }
}
