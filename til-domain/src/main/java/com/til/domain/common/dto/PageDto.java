package com.til.domain.common.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record PageDto<T>(
                         List<T> list,
                         PageInfoDto pageInfo
) {

    public static <T> PageDto<T> of(Page<T> lists) {
        return PageDto.<T>builder()
            .list(lists.getContent())
            .pageInfo(PageInfoDto.of(lists))
            .build();
    }
}
