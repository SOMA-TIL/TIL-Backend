package com.til.domain.category.dto;

import com.til.domain.category.model.Category;

import lombok.Builder;

@Builder
public record CategoryDto(
                          String name,
                          String topic
) {

    public static CategoryDto of(Category category) {
        return CategoryDto.builder()
            .name(category.getName())
            .topic(category.getTopic())
            .build();
    }
}
