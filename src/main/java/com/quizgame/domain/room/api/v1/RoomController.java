package com.quizgame.domain.room.api.v1;

import com.quizgame.domain.room.api.v1.dto.RoomListResponse;
import com.quizgame.domain.room.api.v1.dto.RoomRequest;
import com.quizgame.domain.room.api.v1.dto.RoomResponse;
import com.quizgame.domain.room.service.EnterRoomService;
import com.quizgame.domain.room.service.GetRoomService;
import com.quizgame.domain.room.service.LeaveRoomService;
import com.quizgame.domain.room.service.SearchRoomService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.quizgame.global.constant.WebsocketTopic.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final EnterRoomService enterRoomService;
    private final SearchRoomService searchRoomService;
    private final SimpMessagingTemplate messagingTemplate;
    private final LeaveRoomService leaveRoomService;
    private final GetRoomService getRoomService;

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> roomList(@PathVariable Long roomId) {
        log.info("=== /api/v1/room/{roomId} ===");
        return ResponseEntity.ok(getRoomService.execute(roomId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<RoomListResponse>> roomList() {
        log.info("=== /api/v1/room/list ===");
        return ResponseEntity.ok(searchRoomService.execute());
    }

    @PostMapping("/enter")
    public ResponseEntity<Long> enterRoom(@RequestBody RoomRequest request) {
        log.info("=== /api/v1/room/enter ===");
        Long roomId = enterRoomService.execute(request);
        //방 상태 변경 알림
        messagingTemplate.convertAndSend(ROOM_LIST_TOPIC, "changed");
        return ResponseEntity.ok(roomId);
    }

    @PostMapping("/leave")
    public ResponseEntity<CommonResponse> leaveRoom() {
        log.info("=== /api/v1/room/leave ===");
        Long roomId = leaveRoomService.execute();
        //방 상태 변경 알림
        messagingTemplate.convertAndSend(ROOM_LIST_TOPIC, "changed");
        return ResponseEntity.ok(CommonResponse.from(SystemMessageCode.EMPTY_OK));
    }

}