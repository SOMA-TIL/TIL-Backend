package com.til.domain.problem.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.til.domain.history.dto.TargetCountSummaryDto;

public interface ProblemStatisticsRepositoryCustom {

    void saveHourlyViewStatistics(LocalDateTime targetTime, List<TargetCountSummaryDto> viewSummary);
}
