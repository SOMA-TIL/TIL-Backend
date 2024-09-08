package com.til.application.problem;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.category.model.ProblemCategory;
import com.til.domain.category.repository.CategoryRepository;
import com.til.domain.category.repository.ProblemCategoryRepository;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.AdminCreateProblemDto;
import com.til.domain.problem.dto.AdminUpdateProblemDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProblemService {

    private final ProblemRepository problemRepository;
    private final CategoryRepository categoryRepository;
    private final ProblemCategoryRepository problemCategoryRepository;

    @Transactional
    public void createProblem(AdminCreateProblemDto adminCreateProblemDto) {
        categoryRepository.validateCategoryIds(adminCreateProblemDto.categoryIdList());

        Problem problem = adminCreateProblemDto.toEntity();
        problemRepository.save(problem);

        for (Long categoryId : adminCreateProblemDto.categoryIdList()) {
            ProblemCategory problemCategory = ProblemCategory.builder()
                .problemId(problem.getId())
                .categoryId(categoryId)
                .build();

            problemCategoryRepository.save(problemCategory);
        }
    }

    @Transactional
    public void updateProblem(AdminUpdateProblemDto adminUpdateProblemDto) {
        validateProblemExists(adminUpdateProblemDto.id());
        problemRepository.updateProblem(adminUpdateProblemDto);

        if (adminUpdateProblemDto.categoryIdList() != null && !adminUpdateProblemDto.categoryIdList().isEmpty()) {
            categoryRepository.validateCategoryIds(adminUpdateProblemDto.categoryIdList());
            problemCategoryRepository.updateProblemCategories(adminUpdateProblemDto.id(), adminUpdateProblemDto
                .categoryIdList());
        }
    }

    private void validateProblemExists(Long problemId) {
        boolean isExist = problemRepository.existsById(problemId);
        if (!isExist) {
            throw new BaseException(ProblemErrorCode.NOT_FOUND_PROBLEM);
        }
    }
}
