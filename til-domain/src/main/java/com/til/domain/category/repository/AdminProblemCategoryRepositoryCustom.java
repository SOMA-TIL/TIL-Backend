package com.til.domain.category.repository;

import java.util.List;

public interface AdminProblemCategoryRepositoryCustom {

    void deleteByProblemId(Long problemId);

    void updateProblemCategories(Long problemId, List<Long> categoryIdList);
}
