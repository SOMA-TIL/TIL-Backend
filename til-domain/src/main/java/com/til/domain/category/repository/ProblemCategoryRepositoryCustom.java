package com.til.domain.category.repository;

import java.util.List;

public interface ProblemCategoryRepositoryCustom {

    List<Long> getProblemIdListByCategoryId(Long categoryId);

    void saveAllProblemCategories(Long problemId, List<Long> categoryIdList);
}
