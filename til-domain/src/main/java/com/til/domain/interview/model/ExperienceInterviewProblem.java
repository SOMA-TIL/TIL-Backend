package com.til.domain.interview.model;

import com.til.config.model.BaseTimeEntity;
import com.til.domain.grading.enums.GradingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "experience_interview_problem")
public class ExperienceInterviewProblem extends BaseTimeEntity {

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

    @Column(nullable = false)
    private Long interviewId;

    public static ExperienceInterviewProblem createUnsolvedExperienceInterviewProblem(
        Integer sequence,
        String question,
        Long interviewId
    ) {
        return ExperienceInterviewProblem.builder()
            .status(InterviewProblemStatus.UNSOLVED)
            .gradingStatus(GradingStatus.IDLE)
            .sequence(sequence)
            .question(question)
            .interviewId(interviewId)
            .build();
    }

}
