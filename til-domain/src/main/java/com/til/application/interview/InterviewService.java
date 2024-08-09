package com.til.application.interview;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.model.Interview;
import com.til.domain.interview.repository.InterviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewService {

    private final InterviewRepository interviewRepository;

    @Transactional
    public InterviewCodeDto create(InterviewCreateDto interviewCreateDto) {
        // todo: URL에 사용할 짧고 고유한 code 생성하여 대체
        String code = "fresh code";

        Interview interview = interviewCreateDto.toEntity(code);
        interviewRepository.save(interview);

        // todo: categoryList를 관계 테이블에 저장

        return InterviewCodeDto.of(interview);
    }

}
