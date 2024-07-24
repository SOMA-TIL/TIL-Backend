package com.til.application.problem;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.problem.dto.ProblemListDto;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<ProblemListDto> getProblemList() {
        List<Problem> problemList = problemRepository.findAll();
        return problemList.stream()
            .map(ProblemListDto::of)
            .collect(Collectors.toList());
    }

}
