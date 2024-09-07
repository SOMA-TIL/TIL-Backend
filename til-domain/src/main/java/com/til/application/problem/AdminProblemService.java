package com.til.application.problem;

import com.til.domain.category.model.Category;
import com.til.domain.category.model.ProblemCategory;
import com.til.domain.category.repository.CategoryRepository;
import com.til.domain.category.repository.ProblemCategoryRepository;
import com.til.domain.problem.dto.AdminCreateProblemDto;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProblemService {
    private final ProblemRepository problemRepository;
    private final CategoryRepository categoryRepository;
    private final ProblemCategoryRepository problemCategoryRepository;

    @Transactional
    public void createProblem(AdminCreateProblemDto adminCreateProblemDto) {
        Problem problem = adminCreateProblemDto.toEntity();
        problemRepository.save(problem);

        for (Long categoryId : adminCreateProblemDto.categoryIdList()) {
            Category category = categoryRepository.getById(categoryId);

            ProblemCategory problemCategory = ProblemCategory.builder()
                .problemId(problem.getId())
                .categoryId(category.getId())
                .build();

            problemCategoryRepository.save(problemCategory);
        }
    }
}
