package com.til.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.problem.model.ProblemHourlyViewStatistics;

public interface ProblemStatisticsRepository extends JpaRepository<ProblemHourlyViewStatistics, Long>,
    ProblemStatisticsRepositoryCustom {

}
