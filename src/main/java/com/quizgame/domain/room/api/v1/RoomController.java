package com.quizgame.domain.room.api.v1;

import com.quizgame.domain.room.api.v1.dto.RoomListResponse;
import com.quizgame.domain.room.repository.RoomRepository;
import com.quizgame.domain.room.service.SearchRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final SearchRoomService searchRoomService;

    @GetMapping("/list")
    public ResponseEntity<List<RoomListResponse>> roomList() {
        return ResponseEntity.ok(searchRoomService.execute());
    }

}