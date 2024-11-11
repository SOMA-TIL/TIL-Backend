package com.til.controller.mock;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.domain.interview.dto.CreatingProblemResultDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mock")
public class MockController {

    @PostMapping("/grading-service/grading")
    public ResponseEntity<Object> mockGradingService() {
        Map<String, Object> pass = Map.of("result", "PASS", "comment", "결과는 PASS이고 다음은 제출한 답변에 대한 피드백입니다.");
        Map<String, Object> fail = Map.of("result", "FAIL", "comment", "결과는 FAIL이고 제출한 답변에 대한 피드백입니다.");

        return ResponseEntity.ok((new Random()).nextBoolean() ? pass : fail);
    }

    @PostMapping("/llm-service/creating-problem")
    public ResponseEntity<Object> mockCreatingProblem() {
        Map<String, Object> questionList = Map.of("questionList", List.of(
            CreatingProblemResultDto.create("더미 질문 1", "채점 기준 1"),
            CreatingProblemResultDto.create("더미 질문 2", "채점 기준 2"),
            CreatingProblemResultDto.create("더미 질문 3", "채점 기준 3")));

        return ResponseEntity.ok(questionList);
    }

}
