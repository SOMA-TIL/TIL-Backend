package com.til.domain.category.dto;

import com.til.domain.category.model.Category;

import lombok.Builder;

@Builder
public record CategoryDto(
                          Long id,
                          String tag
) {

    public static CategoryDto of(Category category) {
        return CategoryDto.builder()
            .id(category.getId())
            .tag(category.getTag())
            .build();
    }
}
