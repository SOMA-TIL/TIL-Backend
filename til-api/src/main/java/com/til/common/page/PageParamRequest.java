package com.til.common.page;

import com.til.domain.common.dto.PageParamDto;
import com.til.domain.common.enums.BaseErrorCode;
import com.til.domain.common.exception.BaseException;

import lombok.Builder;

@Builder
public record PageParamRequest(
                               Integer page,
                               Integer size,
                               String sort,
                               String order
) {

    public PageParamRequest {
        page = page != null ? page : 0;
        size = size != null ? size : 10;
        sort = sort != null ? sort : "id";
        order = order != null ? order : "asc";
    }

    public static PageParamRequest of(Integer page, Integer size, String sort, String order) {
        return new PageParamRequest(page, size, sort, order);
    }

    public PageParamDto toServiceDto() {
        return PageParamDto.builder()
            .page(this.page)
            .size(this.size)
            .sort(this.sort)
            .order(this.order)
            .build();
    }

    public void validate() {
        if (page < 0) {
            throw new BaseException(BaseErrorCode.INVALID_PAGE_REQUEST);
        }
        if (size <= 0) {
            throw new BaseException(BaseErrorCode.INVALID_PAGE_REQUEST);
        }
        if (!isValidOrder(order)) {
            throw new BaseException(BaseErrorCode.INVALID_PAGE_REQUEST);
        }
    }

    private boolean isValidOrder(String order) {
        return "asc".equalsIgnoreCase(order) || "desc".equalsIgnoreCase(order);
    }
}
