package com.til.domain.problem.model;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "problem_statistics")
public class ProblemStatistics {

    @Id
    private Long problemId;

    @Column(nullable = false)
    private Long passedCount;

    @Column(nullable = false)
    private Long failedCount;

    @Column(nullable = false)
    private Long attemptCount;

    @Column(nullable = false)
    private Float passRate;
}
