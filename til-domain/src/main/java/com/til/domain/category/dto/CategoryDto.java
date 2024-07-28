package com.til.domain.category.dto;

import lombok.Builder;

@Builder
public record CategoryDto(
                          String name,
                          String topic
) {

}
