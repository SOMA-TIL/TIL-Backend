package com.til.application.grading;

import static com.til.domain.auth.enums.AuthConstants.AUTHORIZATION_HEADER;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.til.domain.grading.dto.GradingInputDataDto;
import com.til.domain.grading.dto.GradingResultDto;
import com.til.domain.grading.enums.AnswerType;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.grading.repository.GradingRepository;
import com.til.domain.problem.repository.UserProblemRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GradingService {

    @Value("${grading-service.api.url}")
    private String GRADING_API_URL;

    @Value("${grading-service.api.key}")
    private String GRADING_API_KEY;

    private final GradingRepository gradingRepository;
    private final UserProblemRepository userProblemRepository;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder().baseUrl(GRADING_API_URL).build();
    }

    @Async
    @Transactional
    public void makeGrading(AnswerType type, Long targetId) {
        GradingInputDataDto gradingInputDataDto = prepareGradingInputDataDto(type, targetId);
        log.info("Grading input data : {}", gradingInputDataDto);

        CompletableFuture<GradingResultDto> gradingResultFuture = sendGradingRequest(gradingInputDataDto);

        gradingResultFuture.thenAccept(result -> {
            gradingRepository.save(GradingResultDto.toEntity(type, targetId, result));
            userProblemRepository.updateStatus(targetId, GradingStatus.COMPLETED);
        }).exceptionally(e -> {
            userProblemRepository.updateStatus(targetId, GradingStatus.ERROR);
            return null;
        });
    }

    public GradingResultDto getGradingResult(AnswerType type, Long targetId) {
        // TODO : 채점 진행 상태 검증 필요
        return gradingRepository.getResultByTypeAndTargetId(type, targetId);
    }

    private GradingInputDataDto prepareGradingInputDataDto(AnswerType type, Long targetId) {
        log.info("Prepare grading input data : type={}, targetId={}", type, targetId);
        return (type == AnswerType.PROBLEM) ? gradingRepository.getGradingInputDataFromUserProblem(targetId)
            : gradingRepository.getGradingInputDataFromInterviewProblem(targetId);
    }

    private CompletableFuture<GradingResultDto> sendGradingRequest(GradingInputDataDto gradingInputDataDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return webClient.post()
                    .header(AUTHORIZATION_HEADER, GRADING_API_KEY)
                    .bodyValue(gradingInputDataDto)
                    .retrieve()
                    .bodyToMono(GradingResultDto.class)
                    .block();
            } catch (Exception e) {
                log.error("Grading request failed: {}", e.getMessage());
                throw new RuntimeException("Grading request failed", e);
            }
        });
    }
}
