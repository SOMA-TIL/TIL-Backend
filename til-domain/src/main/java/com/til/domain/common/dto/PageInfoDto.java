package com.til.domain.common.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record PageInfoDto(
                          int currentPage,
                          int pageSize,
                          long totalItems,
                          int totalPages
) {

    public static <T> PageInfoDto of(Page<T> page) {
        return PageInfoDto.builder()
            .currentPage(page.getNumber())
            .pageSize(page.getSize())
            .totalItems(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();
    }
}
