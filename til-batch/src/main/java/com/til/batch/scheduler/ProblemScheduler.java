package com.til.batch.scheduler;

import static com.til.batch.enums.ScheduleCron.EVERY_HOUR;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class ProblemScheduler {

    private final JobLauncher jobLauncher;

    private final Job problemViewStaticsJob;

    @Scheduled(cron = EVERY_HOUR)
    public void problemViewStaticsJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        String targetTime = getOneHourAgo();
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("targetTime", targetTime)
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        log.info("Starting hourly statistics job for problem views. Target time: {}", targetTime);

        jobLauncher.run(problemViewStaticsJob, jobParameters);
    }

    private static String getOneHourAgo() {
        LocalDateTime time = LocalDateTime.now().minusHours(1).withMinute(0).withSecond(0);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(time);
    }
}
