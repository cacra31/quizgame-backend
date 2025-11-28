package com.quizgame.domain.room.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizgame.domain.room.api.v1.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.quizgame.global.constant.RedisKey.*;

@Service
@RequiredArgsConstructor
public class RoomRedisService {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;



    //방 정보
    public RoomDto getRoom(Long roomId) {
        Object o = redisTemplate.opsForValue().get(ROOM.formatted(roomId));
        return o == null ? null : objectMapper.convertValue(o, RoomDto.class);
    }

    public void setRoom(RoomDto roomDto) {
        redisTemplate.opsForValue().set(ROOM.formatted(roomDto.roomId()), roomDto);
    }

    public void deleteRoom(Long roomId) {
        redisTemplate.delete(ROOM.formatted(roomId));
    }

    //카테고리 별 대기방
    public Long getWaitingRoom(Long categoryId) {
        Number n = (Number) redisTemplate.opsForValue().get(WAITING_ROOM.formatted(categoryId));
        return n == null ? null : n.longValue();
    }

    public void setWaitingRoom(Long categoryId, Long roomId) {
        redisTemplate.opsForValue().set(WAITING_ROOM.formatted(categoryId), roomId);
    }

    public void deleteWaitingRoom(Long categoryId) {
        redisTemplate.delete(WAITING_ROOM.formatted(categoryId));
    }

}
