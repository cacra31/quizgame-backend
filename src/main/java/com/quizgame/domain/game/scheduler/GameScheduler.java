package com.quizgame.domain.game.scheduler;

import com.quizgame.domain.game.api.v1.dto.GameEvent;
import com.quizgame.domain.game.service.FinishGameService;
import com.quizgame.domain.question.api.v1.dto.QuestionDto;
import com.quizgame.domain.question.redis.QuestionRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.quizgame.global.constant.WebsocketTopic.ROOM_EVENT_TOPIC;

@Service
@RequiredArgsConstructor
public class GameScheduler {

    private final TaskScheduler taskScheduler;
    private final QuestionRedisService questionRedisService;
    private final FinishGameService finishGameService;
    private final SimpMessagingTemplate messagingTemplate;

    public void startGame(Long roomId) {
        List<QuestionDto> questions = questionRedisService.getQuestions(roomId);
        messagingTemplate.convertAndSend(ROOM_EVENT_TOPIC.formatted(roomId), GameEvent.gameStart());
        scheduleQuestion(roomId, questions, 0, Instant.now().plusSeconds(3));
    }

    private void scheduleQuestion(Long roomId, List<QuestionDto> questions, int index, Instant startAt) {
        taskScheduler.schedule(() -> {
            if (index >= questions.size()) {
                messagingTemplate.convertAndSend(ROOM_EVENT_TOPIC.formatted(roomId), GameEvent.gameFinish());
                finishGameService.execute();
                return;
            }
            QuestionDto question = questions.get(index);
            if (question.questionType() == 1) {
                question = question.removeAnswers();
            }else {
                question = question.removeCorrectYn();
            }
            messagingTemplate.convertAndSend(ROOM_EVENT_TOPIC.formatted(roomId), GameEvent.questionStart(index, question));

            // 현재 문제의 “정답 입력 마감 시점”
            Instant questionEndTime = Instant.now().plusSeconds(10);

            // 1) 정답 마감 처리 예약
            taskScheduler.schedule(
                    () -> {
                        messagingTemplate.convertAndSend(ROOM_EVENT_TOPIC.formatted(roomId), GameEvent.questionFinish(index));
                    },
                    questionEndTime
            );

            // 2) 다음 문제 시작 예약 (마감 + 인터벌 후)
            Instant nextStart = questionEndTime.plusSeconds(3);
            scheduleQuestion(roomId, questions, index + 1, nextStart);
        }, startAt);
    }


}
