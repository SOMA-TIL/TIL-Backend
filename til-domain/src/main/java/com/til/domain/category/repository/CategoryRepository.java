package com.til.domain.category.repository;

import com.til.domain.category.enums.CategoryErrorCode;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category getById(Long id) {
        return findById(id).orElseThrow(() -> new BaseException(CategoryErrorCode.NOT_FOUND_CATEGORY));
    }
}
