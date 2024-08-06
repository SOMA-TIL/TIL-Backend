package com.til.domain.category.dto;

import com.til.domain.category.model.Category;

import lombok.Builder;

@Builder
public record CategoryDto(
                          String tag
) {

    public static CategoryDto of(Category category) {
        return CategoryDto.builder()
            .tag(category.getTag())
            .build();
    }
}
