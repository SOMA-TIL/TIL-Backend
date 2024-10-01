package com.til.domain.category.repository.admin;

import java.util.List;

public interface AdminCategoryRepositoryCustom {

    void validateCategoryIds(List<Long> categoryIdList);
}
