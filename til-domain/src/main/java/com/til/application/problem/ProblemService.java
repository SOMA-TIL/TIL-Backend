package com.til.application.problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.common.dto.PageParamDto;
import com.til.domain.problem.dto.ProblemInfoDto;
import com.til.domain.problem.dto.ProblemListDto;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemListDto getProblemList(PageParamDto pageParamDto) {
        Pageable pageable = pageParamDto.toPageable();
        Page<Problem> problems = problemRepository.findAll(pageable);
        return ProblemListDto.of(problems);
    }

    public ProblemInfoDto getProblemInfo(Long id) {
        Problem problem = problemRepository.getById(id);
        return ProblemInfoDto.of(problem);
    }

}
