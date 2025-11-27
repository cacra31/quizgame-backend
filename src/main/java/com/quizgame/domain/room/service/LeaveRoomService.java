package com.quizgame.domain.room.service;

import com.quizgame.domain.room.api.v1.dto.RoomDto;
import com.quizgame.domain.room.redis.RoomRedisService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.exception.QuizGameException;
import com.quizgame.global.session.SessionUser;
import com.quizgame.global.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LeaveRoomService {

    private final RoomRedisService roomRedisService;

    public void execute() {
        SessionUser sessionUser = SessionUtil.getSessionUser();
        Long userUuid = sessionUser.id();
        // 유저가 입장한 방 확인
        Long roomId = roomRedisService.getRoomUser(userUuid);
        if(roomId == null) {
            throw new QuizGameException(SystemMessageCode.NOT_IN_ROOM);
        }
        RoomDto room = roomRedisService.getRoom(roomId);
        Set<Long> users = new HashSet<>(room.users());
        users.remove(userUuid);
        roomRedisService.deleteRoomUser(userUuid);
        if (users.isEmpty()){
            roomRedisService.deleteRoom(room.roomId());
            roomRedisService.deleteWaitingRoom(room.categoryId());
        } else {
            // 남은 유저가 있으면 방 정보 업데이트
            RoomDto updated = RoomDto.builder()
                    .roomId(room.roomId())
                    .categoryId(room.categoryId())
                    .categoryName(room.categoryName())
                    .createdAt(room.createdAt())
                    .maxPlayers(room.maxPlayers())
                    .users(users)
                    .build();

            roomRedisService.setRoom(updated);
        }
    }
}
