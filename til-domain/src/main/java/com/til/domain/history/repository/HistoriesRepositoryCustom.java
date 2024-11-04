package com.til.domain.history.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.til.domain.history.dto.TargetCountSummaryDto;

public interface HistoriesRepositoryCustom {

    List<TargetCountSummaryDto> findTargetCountSummary(String target, LocalDateTime start, LocalDateTime emd);
}
