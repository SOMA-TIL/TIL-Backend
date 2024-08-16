package com.til.application.interview;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.category.dto.CategoryDto;
import com.til.domain.category.dto.InterviewCategoryDto;
import com.til.domain.category.repository.InterviewCategoryRepository;
import com.til.domain.category.repository.ProblemCategoryRepository;
import com.til.domain.common.exception.BaseException;
import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.dto.InterviewInfoDto;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;
import com.til.domain.interview.enums.InterviewErrorCode;
import com.til.domain.interview.model.Interview;
import com.til.domain.interview.model.InterviewProblem;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.repository.InterviewProblemRepository;
import com.til.domain.interview.repository.InterviewRepository;
import com.til.utils.random.RandomValueGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final InterviewCategoryRepository interviewCategoryRepository;
    private final InterviewProblemRepository interviewProblemRepository;

    private final ProblemCategoryRepository problemCategoryRepository;

    private static final int RANDOM_ID_SIZE = 11;

    @Transactional
    public InterviewCodeDto createInterview(InterviewCreateDto interviewCreateDto) {
        checkProcessingInterview(interviewCreateDto.userId());

        String code = createRandomId();

        Interview interview = interviewCreateDto.toEntity(code);
        interviewRepository.save(interview);

        createInterviewCategory(interview.getId(), interviewCreateDto.categoryIdList());

        // todo: 카테고리 내부에서 문제를 랜덤으로 선정하도록 구현
        createInterviewProblem(interviewCreateDto.categoryIdList(), interview.getId());

        return InterviewCodeDto.of(interview);
    }

    public InterviewInfoDto getInterviewInfo(Long userId, String code) {
        Interview interview = interviewRepository.getByCode(code);

        validateGetInterviewInfo(userId, interview);

        List<CategoryDto> categoryList = interviewCategoryRepository.getCategoryListByInterviewId(interview.getId());

        List<InterviewProblemQuestionDto> problemList = interviewProblemRepository
            .getInterviewProblemQuestionByInterviewId(interview.getId());

        return InterviewInfoDto.of(interview.getId(), categoryList, problemList);
    }

    private void checkDuplicateCode(String code) {
        if (interviewRepository.existsByCode(code)) {
            throw new BaseException(InterviewErrorCode.FAIL_CREATE_INTERVIEW);
        }
    }

    private void validateGetInterviewInfo(Long userId, Interview interview) {
        if (!userId.equals(interview.getUserId()) || !InterviewStatus.PROCESSING.equals(interview.getStatus())) {
            throw new BaseException(InterviewErrorCode.FAIL_GET_INTERVIEW);
        }
    }

    private void checkProcessingInterview(Long userId) {
        if (interviewRepository.existsByUserIdAndStatus(userId, InterviewStatus.PROCESSING)) {
            throw new BaseException(InterviewErrorCode.ALREADY_PROCESSING_INTERVIEW);
        }
    }

    private void createInterviewCategory(Long interviewId, List<Long> categoryIdList) {
        categoryIdList.forEach((categoryId) -> interviewCategoryRepository.save(InterviewCategoryDto.of(interviewId,
            categoryId).toEntity()));
    }

    private void createInterviewProblem(List<Long> categoryIdList, Long interviewId) {
        List<InterviewProblem> interviewProblemList = new ArrayList<>();

        categoryIdList.forEach((categoryId) -> {
            List<Long> problemIdList = problemCategoryRepository.getProblemIdListByCategoryId(categoryId);

            // todo: Bulk Insert 리팩토링, 문제 Sequence로직 구현
            for (int i = 0; i < problemIdList.size(); i++) {
                interviewProblemList.add(
                    InterviewProblem.createUnsolvedInterviewProblem(i + 1, interviewId, problemIdList.get(i))
                );
            }
        });

        interviewProblemRepository.saveAll(interviewProblemList);
    }

    private String createRandomId() {
        String code = RandomValueGenerator.generateRandomId(RANDOM_ID_SIZE);
        checkDuplicateCode(code);
        return code;
    }

}
