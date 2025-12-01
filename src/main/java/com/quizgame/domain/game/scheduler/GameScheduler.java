package com.quizgame.domain.game.scheduler;

import com.quizgame.domain.game.api.v1.dto.GameEvent;
import com.quizgame.domain.game.service.FinishGameService;
import com.quizgame.domain.question.api.v1.dto.QuestionDto;
import com.quizgame.domain.question.redis.QuestionRedisService;
import com.quizgame.global.code.GameStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.quizgame.global.constant.WebsocketTopic.ROOM_QUESTION_TOPIC;

@Service
@RequiredArgsConstructor
public class GameScheduler {

    private final TaskScheduler taskScheduler;
    private final QuestionRedisService questionRedisService;
    private final FinishGameService finishGameService;
    private final SimpMessagingTemplate messagingTemplate;

    public void startGame(Long roomId) {
        List<QuestionDto> questions = questionRedisService.getQuestions(roomId);
        GameEvent gameEvent = GameEvent.builder()
                .type(GameStatusCode.GAME_STARTED.getCode())
                .build();
        messagingTemplate.convertAndSend(ROOM_QUESTION_TOPIC.formatted(roomId), gameEvent);
        scheduleQuestion(roomId, questions, 0, Instant.now().plusSeconds(3));
    }

    private void scheduleQuestion(Long roomId, List<QuestionDto> questions, int index, Instant startAt) {
        taskScheduler.schedule(() -> {
            if (index >= questions.size()) {
                GameEvent gameFinishedEvent = GameEvent.builder()
                        .type(GameStatusCode.GAME_FINISHED.getCode())
                        .build();
                messagingTemplate.convertAndSend(ROOM_QUESTION_TOPIC.formatted(roomId), gameFinishedEvent);
                return;
            }

            QuestionDto question = questions.get(index);

            GameEvent questionStartEvent = GameEvent.builder()
                    .type(GameStatusCode.QUESTION_STARTED.getCode())
                    .index(index)
                    .question(question)
                    .build();
            messagingTemplate.convertAndSend(ROOM_QUESTION_TOPIC.formatted(roomId), questionStartEvent);

            // 현재 문제의 “정답 입력 마감 시점”
            Instant questionEndTime = Instant.now().plusSeconds(10);

            // 1) 정답 마감 처리 예약
            taskScheduler.schedule(
                    () -> {
                        GameEvent questionEndEvent = GameEvent.builder()
                                .type(GameStatusCode.QUESTION_FINISHED.getCode())
                                .index(index)
                                .build();
                        messagingTemplate.convertAndSend(ROOM_QUESTION_TOPIC.formatted(roomId), questionEndEvent);
                    },
                    questionEndTime
            );

            // 2) 다음 문제 시작 예약 (마감 + 인터벌 후)
            Instant nextStart = questionEndTime.plusSeconds(3);
            scheduleQuestion(roomId, questions, index + 1, nextStart);
        }, startAt);
    }


}
