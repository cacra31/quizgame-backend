package com.quizgame.domain.room.service;

import com.quizgame.domain.category.domain.Category;
import com.quizgame.domain.category.repository.CategoryRepository;
import com.quizgame.domain.room.api.v1.dto.RoomRedisVo;
import com.quizgame.domain.room.api.v1.dto.RoomRequest;
import com.quizgame.domain.room.redis.RoomRedisService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.exception.QuizGameException;
import com.quizgame.global.redis.RedisSequenceService;
import com.quizgame.global.session.SessionUser;
import com.quizgame.global.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.quizgame.global.util.SessionUtil.LOGIN_USER;

@Service
@RequiredArgsConstructor
public class EnterRoomService {

    private final CategoryRepository categoryRepository;
    private final RoomRedisService roomRedisService;
    private final RedisSequenceService redisSequenceService;

    public RoomRedisVo execute(RoomRequest request) {
        SessionUser sessionUser = (SessionUser) SessionUtil.getAttribute(LOGIN_USER);
        if (sessionUser == null) {
            throw new QuizGameException(SystemMessageCode.UNAUTHORIZED);
        }
        Long userUuid = sessionUser.id();
        // 레디스에서 다른 방에 유저가 입장해있는 지 확인
        if (roomRedisService.getRoomUser(userUuid) != null) {
            throw new QuizGameException(SystemMessageCode.ALREADY_IN_ROOM);
        }

        RoomRedisVo room = null;

        // 해당 카테고리에 대기방 있는지 조회
        Long waitingRoomId = roomRedisService.getWaitingRoom(request.categoryId());
        if (waitingRoomId != null) {
            room = roomRedisService.getRoom(waitingRoomId);
        }

        // 대기방 없을 경우 새로 생성
        if (room == null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new QuizGameException(SystemMessageCode.NOT_FOUND));
            room = RoomRedisVo.builder()
                    .roomId(redisSequenceService.nextRoomId())
                    .categoryId(category.getId())
                    .categoryName(category.getName())
                    .createdAt(LocalDateTime.now())
                    .build();
            //방정보 저장
            roomRedisService.setRoom(room);
            //대기방 저장
            roomRedisService.setWaitingRoom(category.getId(), room.roomId());
        }
        // 유저 입장정보 레디스에 저장
        roomRedisService.setRoomUser(userUuid, room.roomId());

        return room;
    }
}
