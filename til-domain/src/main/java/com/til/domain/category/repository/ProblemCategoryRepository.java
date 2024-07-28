package com.til.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.category.model.ProblemCategory;

public interface ProblemCategoryRepository extends JpaRepository<ProblemCategory, Long> {

}
