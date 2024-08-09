package com.til.domain.grading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.grading.model.Grading;

public interface GradingRepository extends JpaRepository<Grading, Long>, GradingRepositoryCustom {

}
