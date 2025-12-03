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
        CompletableFuture<Boolean> generateQuestionFuture = generateQuestionService.generateQuestion(roomRedisService.getRoom(roomId));
        // 60초 대기
        LocalDateTime startAt = createdAt.plusSeconds(60);
        taskScheduler.schedule(
                () -> {
                    Boolean success;
                    try {
                        success = generateQuestionFuture.get(10, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        success = false;
                    }
                    if (!success) {
                        throw new QuizGameException(SystemMessageCode.INTERNAL_SERVER_ERROR);
                    }
                    handleStart(roomId);
                },
                startAt.atZone(ZoneId.systemDefault()).toInstant()
        );
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
