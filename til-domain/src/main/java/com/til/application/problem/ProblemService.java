package com.til.application.problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.problem.dto.ProblemPageDto;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemPageDto getProblemList(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Problem> problems = problemRepository.findAll(pageable);
        return ProblemPageDto.of(problems);
    }

}
