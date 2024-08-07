package com.til.domain.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.interview.model.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

}
