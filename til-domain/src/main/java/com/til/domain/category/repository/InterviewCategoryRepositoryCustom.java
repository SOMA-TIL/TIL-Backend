package com.til.domain.category.repository;

import java.util.List;

import com.til.domain.category.dto.CategoryDto;

public interface InterviewCategoryRepositoryCustom {

    List<CategoryDto> getCategoryListByInterviewId(Long interviewId);

}
