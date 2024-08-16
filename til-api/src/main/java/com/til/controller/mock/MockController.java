package com.til.controller.mock;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mock")
public class MockController {

    @PostMapping("/grading-service/grading")
    public ResponseEntity<Object> mockGradingService() {
        Map<String, Object> response = Map.of("result", "PASS", "comment", "제출한 답변에 대한 피드백입니다.");
        return ResponseEntity.ok(response);
    }
}
