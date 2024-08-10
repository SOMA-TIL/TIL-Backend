package com.til.application.interview;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.common.exception.BaseException;
import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.enums.InterviewErrorCode;
import com.til.domain.interview.model.Interview;
import com.til.domain.interview.repository.InterviewRepository;
import com.til.utils.random.RandomValueGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewService {

    private final InterviewRepository interviewRepository;

    private static final int RANDOM_ID_SIZE = 11;

    @Transactional
    public InterviewCodeDto create(InterviewCreateDto interviewCreateDto) {
        String code = createRandomId();

        Interview interview = interviewCreateDto.toEntity(code);
        interviewRepository.save(interview);

        // todo: categoryList를 관계 테이블에 저장

        return InterviewCodeDto.of(interview);
    }

    public void checkCode(String code) {
        if (interviewRepository.existsByCode(code)) {
            throw new BaseException(InterviewErrorCode.FAIL_CREATE_INTERVIEW);
        }
    }

    private String createRandomId() {
        String code = RandomValueGenerator.generateRandomId(RANDOM_ID_SIZE);
        checkCode(code);
        return code;
    }

}
