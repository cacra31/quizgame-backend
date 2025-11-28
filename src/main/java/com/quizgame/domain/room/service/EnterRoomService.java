package com.quizgame.domain.room.service;

import com.quizgame.domain.category.domain.Category;
import com.quizgame.domain.category.repository.CategoryRepository;
import com.quizgame.domain.question.service.GenerateQuestionService;
import com.quizgame.domain.room.api.v1.dto.RoomDto;
import com.quizgame.domain.room.api.v1.dto.RoomRequest;
import com.quizgame.domain.room.redis.RoomRedisService;
import com.quizgame.domain.room.scheduler.RoomScheduler;
import com.quizgame.domain.user.redis.UserRedisService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.exception.QuizGameException;
import com.quizgame.global.redis.RedisSequenceService;
import com.quizgame.global.session.SessionUser;
import com.quizgame.global.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.quizgame.global.constant.GlobalConst.MAX_PLAYER;
import static com.quizgame.global.constant.WebsocketTopic.ROOM_USERS_TOPIC;

@Service
@RequiredArgsConstructor
public class EnterRoomService {

    private final CategoryRepository categoryRepository;
    private final RoomRedisService roomRedisService;
    private final RedisSequenceService redisSequenceService;
    private final RoomScheduler roomScheduler;
    private final GenerateQuestionService generateQuestionService;
    private final UserRedisService userRedisService;
    private final SimpMessagingTemplate messagingTemplate;

    public Long execute(RoomRequest request) {
        SessionUser sessionUser = SessionUtil.getSessionUser();
        Long userUuid = sessionUser.id();
        // 레디스에서 다른 방에 유저가 입장해있는 지 확인
        if (userRedisService.getRoomUser(userUuid) != null) {
            throw new QuizGameException(SystemMessageCode.ALREADY_IN_ROOM);
        }

        RoomDto room = null;

        // 해당 카테고리에 대기방 있는지 조회
        Long waitingRoomId = roomRedisService.getWaitingRoom(request.categoryId());
        if (waitingRoomId != null) {
            room = roomRedisService.getRoom(waitingRoomId);
        }

        // 대기방 없을 경우 새로 생성
        if (room == null) {
            Set<Long> users = new HashSet<>();
            users.add(sessionUser.id());
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new QuizGameException(SystemMessageCode.NOT_FOUND));
            room = RoomDto.builder()
                    .roomId(redisSequenceService.nextRoomId())
                    .categoryId(category.getId())
                    .categoryName(category.getName())
                    .createdAt(LocalDateTime.now())
                    .maxPlayers(MAX_PLAYER)
                    .users(users)
                    .build();
            // 방정보 저장
            roomRedisService.setRoom(room);
            // 대기방 저장
            roomRedisService.setWaitingRoom(category.getId(), room.roomId());
            // 60초 뒤 시작 Task 예약
            roomScheduler.registerStartTask(room.roomId(), room.createdAt());
            // 문제 생성
            generateQuestionService.generateQuestion(room);
        } else {
            // 이미 방 있을 경우 최대인원 확인
            Set<Long> users = new HashSet<>(room.users());
            if (users.size() == room.maxPlayers()) {
                throw new QuizGameException(SystemMessageCode.ROOM_CAPACITY_EXCEEDED);
            }
            users.add(sessionUser.id());
            // 가득 차면 대기방에서 제거
            if (users.size() == room.maxPlayers()) {
                roomRedisService.deleteWaitingRoom(room.categoryId());
            }

            // 방정보 저장
            room = RoomDto.builder()
                    .roomId(room.roomId())
                    .categoryId(room.categoryId())
                    .categoryName(room.categoryName())
                    .createdAt(room.createdAt())
                    .maxPlayers(room.maxPlayers())
                    .users(users)
                    .build();
            roomRedisService.setRoom(room);
        }
        // 유저정보 캐싱
        userRedisService.cacheUserProfile(sessionUser);
        // 유저 입장정보 레디스에 저장
        userRedisService.setRoomUser(userUuid, room.roomId());
        // 방 유저 변경사항 브로드캐스트
        messagingTemplate.convertAndSend(ROOM_USERS_TOPIC.formatted(room.roomId()), userRedisService.getRoomUserProfiles(room.roomId()));
        return room.roomId();
    }
}
