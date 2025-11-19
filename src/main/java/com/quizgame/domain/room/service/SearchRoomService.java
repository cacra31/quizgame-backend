package com.quizgame.domain.room.service;

import com.quizgame.domain.room.api.v1.dto.RoomListResponse;
import com.quizgame.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchRoomService {

    private final RoomRepository roomRepository;

    public List<RoomListResponse> execute() {
        return roomRepository.findRoomList();
    }

}
