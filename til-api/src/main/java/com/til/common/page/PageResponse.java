package com.til.common.page;

import java.util.List;

import com.til.domain.common.dto.PageInfoDto;

import lombok.Builder;

@Builder
public record PageResponse<T>(
                              List<T> items,
                              PageInfoDto pageInfo
) {

    public static <T> PageResponse<T> of(List<T> items, PageInfoDto pageInfo) {
        return PageResponse.<T>builder()
            .items(items)
            .pageInfo(pageInfo)
            .build();
    }
}
