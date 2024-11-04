package com.til.domain.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.history.model.Histories;

public interface HistoriesRepository extends JpaRepository<Histories, Long>, HistoriesRepositoryCustom {

}
