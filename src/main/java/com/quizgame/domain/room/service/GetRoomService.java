package com.quizgame.domain.room.service;

import com.quizgame.domain.room.api.v1.dto.RoomDto;
import com.quizgame.domain.room.api.v1.dto.RoomResponse;
import com.quizgame.domain.room.redis.RoomRedisService;
import com.quizgame.domain.user.api.v1.dto.UserDto;
import com.quizgame.domain.user.redis.UserRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRoomService {

    private final RoomRedisService roomRedisService;
    private final UserRedisService userRedisService;

    public RoomResponse execute(Long roomId) {
        RoomDto room = roomRedisService.getRoom(roomId);
        List<UserDto> roomUserProfiles = userRedisService.getRoomUserProfiles(roomId);
        return RoomResponse.builder()
                .roomId(room.roomId())
                .categoryId(room.categoryId())
                .categoryName(room.categoryName())
                .createdAt(room.createdAt())
                .users(roomUserProfiles)
                .build();
    }

}
