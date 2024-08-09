package com.til.application.grading;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.grading.dto.GradingInputDataDto;
import com.til.domain.grading.dto.GradingResultDto;
import com.til.domain.grading.enums.AnswerType;
import com.til.domain.grading.enums.GradingResult;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.grading.model.Grading;
import com.til.domain.grading.repository.GradingRepository;
import com.til.domain.problem.repository.UserProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradingService {

    private final GradingRepository gradingRepository;
    private final UserProblemRepository userProblemRepository;

    @Transactional
    public void makeGrading(AnswerType type, Long targetId) {
        // TODO : 채점 대상의 정보를 가져옴
        GradingInputDataDto gradingInputDataDto = prepareGradingInputDataDto(type, targetId);

        // TODO : 채점 LLM 요청 후 채점 결과를 받아와야 함
        GradingResult result = getRandomGradingResult();
        String comment = getRandomComment();

        userProblemRepository.updateStatus(targetId, GradingStatus.COMPLETED);

        // TEMP : 채점 결과를 저장
        Grading grading = Grading.builder()
            .targetId(targetId)
            .type(type)
            .result(result)
            .comment(comment)
            .build();
        gradingRepository.save(grading);
    }

    public GradingResultDto getGradingResult(AnswerType type, Long targetId) {
        // TODO : 채점 진행 상태 검증 필요
        return gradingRepository.getResultByTypeAndTargetId(type, targetId);
    }

    private GradingInputDataDto prepareGradingInputDataDto(AnswerType type, Long targetId) {
        if (type == AnswerType.PROBLEM) {
            return gradingRepository.getGradingInputDataFromInterviewProblem(targetId);
        }
        return gradingRepository.getGradingInputDataFromUserProblem(targetId);
    }

    private GradingResult getRandomGradingResult() {
        Random random = new Random();
        int randomIndex = random.nextInt(GradingResult.values().length);
        return GradingResult.values()[randomIndex];
    }

    private String getRandomComment() {
        return "사용자 입력에 대한 피드백입니다.";
    }

}
