package com.quizgame.domain.room.service;

import com.quizgame.domain.category.repository.CategoryRepository;
import com.quizgame.domain.room.api.v1.dto.RoomListResponse;
import com.quizgame.domain.room.api.v1.dto.RoomRedisVo;
import com.quizgame.domain.room.redis.RoomRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchRoomService {

    private final CategoryRepository categoryRepository;
    private final RoomRedisService roomRedisService;

    public List<RoomListResponse> execute() {
        List<RoomListResponse> roomListResponses = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            Long waitingRoomId = roomRedisService.getWaitingRoom(category.getId());
            if (waitingRoomId != null) {
                RoomRedisVo waitingRoom = roomRedisService.getRoom(waitingRoomId);
                roomListResponses.add(RoomListResponse.builder()
                        .roomId(waitingRoom.roomId())
                        .currentPlayer(waitingRoom.users().size())
                        .categoryId(category.getId())
                        .categoryName(category.getName())
                        .createdAt(waitingRoom.createdAt())
                        .maxPlayer(waitingRoom.maxPlayers())
                        .build());
            } else {
                roomListResponses.add(RoomListResponse.builder()
                        .categoryId(category.getId())
                        .categoryName(category.getName())
                        .build());
            }
        });
        return roomListResponses;
    }

}
