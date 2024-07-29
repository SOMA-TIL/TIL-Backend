package com.til.domain.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Builder;

@Builder
public record PageParamDto(
                           int page,
                           int size,
                           String sort,
                           String order
) {

    public Pageable toPageable() {
        Sort.Direction direction = Sort.Direction.fromString(order);
        return PageRequest.of(page, size, Sort.by(direction, sort));
    }

}
