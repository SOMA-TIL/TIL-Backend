package com.til.domain.category.repository;

import java.util.List;

public interface AdminCategoryRepositoryCustom {

    void validateCategoryIds(List<Long> categoryIdList);
}
