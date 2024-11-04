package com.til.domain.history.dto;

import lombok.Builder;

@Builder
public record TargetCountSummaryDto(
                                    String target,
                                    Long targetId,
                                    Long count
) {

}
