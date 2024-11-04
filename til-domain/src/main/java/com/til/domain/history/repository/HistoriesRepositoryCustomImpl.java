package com.til.domain.history.repository;

import static com.til.domain.history.model.QHistories.histories;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.til.domain.history.dto.TargetCountSummaryDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HistoriesRepositoryCustomImpl implements HistoriesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TargetCountSummaryDto> findTargetCountSummary(String target, LocalDateTime start, LocalDateTime emd) {
        return queryFactory
            .select(Projections.constructor(
                TargetCountSummaryDto.class,
                histories.target,
                histories.targetId,
                histories.count()
            ))
            .from(histories)
            .groupBy(histories.target, histories.targetId)
            .where(histories.target.eq(target), histories.createdDate.between(start, emd))
            .fetch();
    }
}
