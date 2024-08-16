package com.til.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.category.model.InterviewCategory;

public interface InterviewCategoryRepository extends JpaRepository<InterviewCategory, Long>,
    InterviewCategoryRepositoryCustom {

}
