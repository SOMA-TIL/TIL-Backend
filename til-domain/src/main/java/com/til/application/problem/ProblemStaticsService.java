package com.til.application.problem;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.history.dto.TargetCountSummaryDto;
import com.til.domain.history.repository.HistoriesRepository;
import com.til.domain.problem.repository.ProblemStatisticsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemStaticsService {

    private final HistoriesRepository historiesRepository;

    private final ProblemStatisticsRepository problemStatisticsRepository;

    public List<TargetCountSummaryDto> problemViewHourStatics(LocalDateTime targetTime) {
        return historiesRepository.findTargetCountSummary("PROBLEM_INFO", targetTime,
            targetTime.plusHours(1).minusNanos(1));
    }

    @Transactional
    public void saveHourlyViewStatistics(LocalDateTime targetTime, List<TargetCountSummaryDto> countSummary) {
        problemStatisticsRepository.saveHourlyViewStatistics(targetTime, countSummary);
    }
}
