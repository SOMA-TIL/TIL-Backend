package com.til.common.page;

import com.til.domain.common.dto.PageParamDto;

import lombok.Builder;

@Builder
public record PageParamRequest(
                               Integer page,
                               Integer size,
                               String sort,
                               String order) {

    public PageParamDto toServiceDto() {
        return PageParamDto.builder()
            .page(this.page != null ? this.page : 0)
            .size(this.size != null ? this.size : 10)
            .sort(this.sort != null ? this.sort : "id")
            .order(this.order != null ? this.order : "asc")
            .build();
    }
}
