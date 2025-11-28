package com.quizgame.domain.game.service;

import com.quizgame.domain.question.api.v1.dto.QuestionDto;
import com.quizgame.domain.question.redis.QuestionRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameScheduler {

    private final TaskScheduler taskScheduler;
    private final QuestionRedisService questionRedisService;

    public void startGame(Long roomId) {
        List<QuestionDto> questions = questionRedisService.getQuestions(roomId);
        scheduleQuestion(roomId, questions, 0, Instant.now());
    }

    private void scheduleQuestion(Long roomId, List<QuestionDto> questions, int index, Instant startAt) {
        if (index >= questions.size()) {
            // 모든 문제 끝 → 게임 종료 처리 (게임종료 서비스)
            return;
        }
        QuestionDto question = questions.get(index);

        // index번째 문제 보내는 작업을 startAt에 예약
        taskScheduler.schedule(() -> {
            // 현재 문제의 “정답 입력 마감 시점”
            Instant questionEndTime = Instant.now().plusSeconds(10);

            // 1) 정답 마감 처리 예약
            taskScheduler.schedule(
                    () -> {

                    }, questionEndTime);
            // 2) 다음 문제 시작 예약 (마감 + 인터벌 후)
            Instant nextStart = questionEndTime.plusSeconds(3);
            scheduleQuestion(roomId, questions, index + 1, nextStart);
        }, startAt);
    }

}
