package com.quizgame.domain.room.api.v1;

import com.quizgame.domain.room.api.v1.dto.RoomListResponse;
import com.quizgame.domain.room.api.v1.dto.RoomRequest;
import com.quizgame.domain.room.api.v1.dto.RoomResponse;
import com.quizgame.domain.room.service.EnterRoomService;
import com.quizgame.domain.room.service.LeaveRoomService;
import com.quizgame.domain.room.service.SearchRoomService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.response.CommonResponse;
import com.quizgame.global.session.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final EnterRoomService enterRoomService;
    private final SearchRoomService searchRoomService;
    private final SimpMessagingTemplate messagingTemplate;
    private final LeaveRoomService leaveRoomService;

    @GetMapping("/list")
    public ResponseEntity<List<RoomListResponse>> enterRoom() {
        return ResponseEntity.ok(searchRoomService.execute());
    }

    @PostMapping("/enter")
    public ResponseEntity<RoomResponse> enterRoom(@RequestBody RoomRequest request) {
        RoomResponse roomResponse = RoomResponse.fromRedisVo(enterRoomService.execute(request));
        //방 상태 변경 알림
        messagingTemplate.convertAndSend("/topic/room-list", "changed");
        return ResponseEntity.ok(roomResponse);
    }

    @PostMapping("/leave")
    public ResponseEntity<CommonResponse> leaveRoom(@RequestBody RoomRequest request) {
        leaveRoomService.execute();
        //방 상태 변경 알림
        messagingTemplate.convertAndSend("/topic/room-list", "changed");
        return ResponseEntity.ok(CommonResponse.from(SystemMessageCode.EMPTY_OK));
    }

}