package com.til.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
