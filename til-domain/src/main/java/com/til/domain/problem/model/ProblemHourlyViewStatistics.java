package com.til.domain.problem.model;

import java.time.LocalDate;

import com.til.config.model.BaseCreatedTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "problem_hourly_view_statistics")
public class ProblemHourlyViewStatistics extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate statisticDate;

    @Column(nullable = false)
    @Min(0)
    @Max(23)
    private Integer statisticHour;

    @Column(nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private Long viewCount;
}
