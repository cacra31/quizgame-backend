package com.quizgame.domain.room.scheduler;

import com.quizgame.domain.game.scheduler.GameScheduler;
import com.quizgame.domain.question.service.GenerateQuestionService;
import com.quizgame.domain.room.api.v1.dto.RoomDto;
import com.quizgame.domain.room.redis.RoomRedisService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.exception.QuizGameException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RoomScheduler {

    private final TaskScheduler taskScheduler;
    private final RoomRedisService roomRedisService;
    private final SimpMessagingTemplate messagingTemplate;
    private final GameScheduler gameScheduler;
    private final GenerateQuestionService generateQuestionService;

    public void registerStartTask(Long roomId, LocalDateTime createdAt) {
        CompletableFuture<Boolean> future = generateQuestionService.generateQuestion(roomRedisService.getRoom(roomId));

        Instant startInstant = createdAt.plusSeconds(60)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        taskScheduler.schedule(() -> {
            // 60초 시점 이후에 시작할 수 있도록 게이트 역할
            future.whenComplete((success, ex) -> {
                if (ex != null || Boolean.FALSE.equals(success)) {
                    // 여기서는 throw 하지 말고 실패 처리(상태 변경/이벤트 전송) 추천
                    //handleStartFail(roomId);
                    return;
                }
                handleStart(roomId);
            });
        }, startInstant);
    }

    private void handleStart(Long roomId) {
        RoomDto room = roomRedisService.getRoom(roomId);
        if (Objects.equals(roomRedisService.getWaitingRoom(room.categoryId()), room.roomId())) {
            roomRedisService.deleteWaitingRoom(room.categoryId());
        }
        messagingTemplate.convertAndSend("/topic/room-list", "changed");
        gameScheduler.startGame(roomId);
    }
}
