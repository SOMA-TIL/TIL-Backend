package com.til.domain.category.model;

import com.til.domain.common.model.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "problem_category")
public class ProblemCategory extends BaseTimeEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private Long categoryId;

}
