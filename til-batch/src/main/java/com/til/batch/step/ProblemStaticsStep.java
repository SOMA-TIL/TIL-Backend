package com.til.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.til.batch.task.ProblemStaticsTask;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProblemStaticsStep extends DefaultBatchConfiguration {

    private final ProblemStaticsTask problemStaticsTask;

    @Bean
    public Step hourViewStatics(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("hourViewStatics", jobRepository)
            .tasklet(problemStaticsTask.problemViewHourStatics(), transactionManager)
            .build();
    }
}
