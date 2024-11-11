package com.til.domain.interview.model;

import com.til.config.model.BaseTimeEntity;
import com.til.domain.grading.enums.GradingStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "interview_problem")
public class InterviewProblem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = true)
    private String answer;

    @Column(nullable = false)
    private Integer sequence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewProblemStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GradingStatus gradingStatus;

    @Column(nullable = true)
    private String gradingCriteria;

    @Column(nullable = false)
    private Long interviewId;

    @Column(nullable = true)
    private Long problemId;

    public static InterviewProblem createUnsolvedInterviewProblem(
        String question,
        String gradingCriteria,
        Integer sequence,
        Long interviewId,
        Long problemId
    ) {
        return InterviewProblem.builder()
            .question(question)
            .status(InterviewProblemStatus.UNSOLVED)
            .gradingStatus(GradingStatus.IDLE)
            .gradingCriteria(gradingCriteria)
            .sequence(sequence)
            .interviewId(interviewId)
            .problemId(problemId)
            .build();
    }

    public static InterviewProblem createUnsolvedInterviewProblemWithPortfolio(
        String question,
        String gradingCriteria,
        Integer sequence,
        Long interviewId
    ) {
        return InterviewProblem.builder()
            .question(question)
            .status(InterviewProblemStatus.UNSOLVED)
            .gradingStatus(GradingStatus.IDLE)
            .gradingCriteria(gradingCriteria)
            .sequence(sequence)
            .interviewId(interviewId)
            .problemId(null)
            .build();
    }

}
