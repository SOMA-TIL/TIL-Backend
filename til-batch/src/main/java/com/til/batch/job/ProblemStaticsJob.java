package com.til.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProblemStaticsJob extends DefaultBatchConfiguration {

    @Bean
    public Job problemViewStaticsJob(JobRepository jobRepository, Step hourViewStatics) {
        return new JobBuilder("problemViewStaticsJob", jobRepository)
            .start(hourViewStatics)
            // TODO : hourViewStatics step으로 인해 생성된 1시간 조회수 데이터를 활용해 랭킹 데이터를 생성/업데이트하는 step 추가
            .build();
    }
}
