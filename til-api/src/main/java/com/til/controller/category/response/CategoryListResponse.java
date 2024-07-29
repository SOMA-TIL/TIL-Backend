package com.til.controller.category.response;

import java.util.List;

import com.til.domain.category.dto.CategoryDto;

public record CategoryListResponse(
                                   List<CategoryDto> categoryList
) {

    public static CategoryListResponse of(List<CategoryDto> categoryList) {
        return new CategoryListResponse(categoryList);
    }
}
