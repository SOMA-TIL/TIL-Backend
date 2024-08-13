package com.til.application.interview;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.category.dto.InterviewCategoryDto;
import com.til.domain.category.repository.InterviewCategoryRepository;
import com.til.domain.common.exception.BaseException;
import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.enums.InterviewErrorCode;
import com.til.domain.interview.model.Interview;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.repository.InterviewRepository;
import com.til.utils.random.RandomValueGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final InterviewCategoryRepository interviewCategoryRepository;

    private static final int RANDOM_ID_SIZE = 11;

    @Transactional
    public InterviewCodeDto createInterview(InterviewCreateDto interviewCreateDto) {
        checkProcessingInterview(interviewCreateDto.userId());

        String code = createRandomId();

        Interview interview = interviewCreateDto.toEntity(code);
        interviewRepository.save(interview);

        createInterviewCategory(interview.getId(), interviewCreateDto.categoryIdList());

        // todo: interview_problem 생성
        // 1. categoryIdList 조건으로 해당하는 문제 id 리스트 가져온다.
        // 2. interview_problem 생성하여 삽입(초기상태 UNSOLVED), 문제 순서 랜덤 부여

        return InterviewCodeDto.of(interview);
    }

    public void checkCode(String code) {
        if (interviewRepository.existsByCode(code)) {
            throw new BaseException(InterviewErrorCode.FAIL_CREATE_INTERVIEW);
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

    private String createRandomId() {
        String code = RandomValueGenerator.generateRandomId(RANDOM_ID_SIZE);
        checkCode(code);
        return code;
    }

}
