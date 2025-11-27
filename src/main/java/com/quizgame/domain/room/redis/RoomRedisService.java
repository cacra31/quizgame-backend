package com.quizgame.domain.room.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizgame.domain.room.api.v1.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomRedisService {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String ROOM = "quiz:room:";
    private static final String WAITING_ROOM = "quiz:room:waiting:";
    private static final String ROOM_USER = "quiz:room:user:";

    //방 정보
    public RoomDto getRoom(Long roomId) {
        Object o = redisTemplate.opsForValue().get(ROOM + roomId);
        return o == null ? null : objectMapper.convertValue(o, RoomDto.class);
    }

    public void setRoom(RoomDto roomDto) {
        redisTemplate.opsForValue().set(ROOM + roomDto.roomId(), roomDto);
    }

    public void deleteRoom(Long roomId) {
        redisTemplate.delete(ROOM + roomId);
    }

    //카테고리 별 대기방
    public Long getWaitingRoom(Long categoryId) {
        Number n = (Number) redisTemplate.opsForValue().get(WAITING_ROOM + categoryId);
        return n == null ? null : n.longValue();
    }

    public void setWaitingRoom(Long categoryId, Long roomId) {
        redisTemplate.opsForValue().set(WAITING_ROOM + categoryId, roomId);
    }

    public void deleteWaitingRoom(Long categoryId) {
        redisTemplate.delete(WAITING_ROOM + categoryId);
    }

    //유저 입장 방
    public Long getRoomUser(Long userUuid) {
        Number n = (Number) redisTemplate.opsForValue().get(ROOM_USER + userUuid);
        return n == null ? null : n.longValue();
    }

    public void setRoomUser(Long userUuid, Long roomId) {
        redisTemplate.opsForValue().set(ROOM_USER + userUuid, roomId);
    }

    public void deleteRoomUser(Long userUuid) {
        redisTemplate.delete(ROOM_USER + userUuid);
    }


}
