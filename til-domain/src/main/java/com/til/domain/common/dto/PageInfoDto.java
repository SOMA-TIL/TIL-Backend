package com.til.domain.common.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record PageInfoDto(
                          int page,
                          int size,
                          long totalElements,
                          int totalPages
) {

    public static <T> PageInfoDto of(Page<T> page) {
        return PageInfoDto.builder()
            .page(page.getNumber())
            .size(page.getSize())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();
    }
}
