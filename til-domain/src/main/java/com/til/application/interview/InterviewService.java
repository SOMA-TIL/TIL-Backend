package com.til.application.interview;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.dto.InterviewUuidDto;
import com.til.domain.interview.model.Interview;
import com.til.domain.interview.repository.InterviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewService {

    private final InterviewRepository interviewRepository;

    @Transactional
    public InterviewUuidDto create(InterviewCreateDto interviewCreateDto) {
        // todo: URL에 사용할 짧고 고유한 uuid 생성하여 대체
        String uuid = "fresh uuid";

        Interview interview = interviewCreateDto.toEntity(uuid);
        interviewRepository.save(interview);

        // todo: categoryList를 관계 테이블에 저장

        return InterviewUuidDto.of(interview);
    }

}
