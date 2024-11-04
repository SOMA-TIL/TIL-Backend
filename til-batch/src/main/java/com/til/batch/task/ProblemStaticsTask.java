package com.til.batch.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.til.application.problem.ProblemStaticsService;
import com.til.domain.history.dto.TargetCountSummaryDto;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProblemStaticsTask {

    private final ProblemStaticsService problemStaticsService;

    @Bean
    public Tasklet problemViewHourStatics() {
        return (contribution, chunkContext) -> {
            LocalDateTime targetTime = LocalDateTime.parse(
                chunkContext.getStepContext().getJobParameters().get("targetTime").toString(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            List<TargetCountSummaryDto> targetCountSummary = problemStaticsService.problemViewHourStatics(targetTime);

            problemStaticsService.saveHourlyViewStatistics(targetTime, targetCountSummary);
            return RepeatStatus.FINISHED;
        };
    }
}
