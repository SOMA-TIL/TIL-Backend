package com.til.domain.problem.dto;

import com.til.domain.category.model.Category;
import com.til.domain.category.model.ProblemCategory;
import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemListDto(
                             Long id,
                             String title,
                             int level,
                             int solved,
                             double percentage,
                             String categoryName,
                             String topic
) {

    public static ProblemListDto of(Problem problem) {
        ProblemCategory problemCategory = problem.getProblemCategorySet().stream().findFirst().orElse(null);
        Category category = (problemCategory != null) ? problemCategory.getCategory() : null;

        return ProblemListDto.builder()
            .id(problem.getId())
            .title(problem.getTitle())
            .level(problem.getLevel())
            .solved(problem.getSolved())
            .percentage(problem.getPercentage())
            .categoryName(category != null ? category.getName() : null)
            .topic(category != null ? category.getTopic() : null)
            .build();
    }
}
