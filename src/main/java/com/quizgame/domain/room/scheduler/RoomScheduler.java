package com.quizgame.domain.room.scheduler;

import com.quizgame.domain.room.api.v1.dto.RoomDto;
import com.quizgame.domain.room.redis.RoomRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class RoomScheduler {

    private final TaskScheduler taskScheduler;
    private final RoomRedisService roomRedisService;
    private final SimpMessagingTemplate messagingTemplate;

    public void registerStartTask(Long roomId, LocalDateTime createdAt) {
        // createdAt + 60초
        LocalDateTime startAt = createdAt.plusSeconds(60);
        taskScheduler.schedule(
                () -> handleStart(roomId),
                startAt.atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    private void handleStart(Long roomId) {
        RoomDto room = roomRedisService.getRoom(roomId);
        roomRedisService.deleteWaitingRoom(room.categoryId());
        messagingTemplate.convertAndSend("/topic/room-list", "changed");
    }
}
