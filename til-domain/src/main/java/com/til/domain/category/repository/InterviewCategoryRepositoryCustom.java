package com.til.domain.category.repository;

import java.util.List;

public interface InterviewCategoryRepositoryCustom {

    List<Long> getCategoryIdListByInterviewId(Long interviewId);

}
