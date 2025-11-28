package com.quizgame.domain.user.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizgame.domain.room.api.v1.dto.RoomDto;
import com.quizgame.domain.user.api.v1.dto.UserDto;
import com.quizgame.global.session.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.quizgame.global.constant.RedisKey.*;

@Service
@RequiredArgsConstructor
public class UserRedisService {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    //유저 입장 방
    public Long getRoomUser(Long userUuid) {
        Number n = (Number) redisTemplate.opsForValue().get(ROOM_USER.formatted(userUuid));
        return n == null ? null : n.longValue();
    }

    public void setRoomUser(Long userUuid, Long roomId) {
        redisTemplate.opsForValue().set(ROOM_USER.formatted(userUuid), roomId);
    }

    public void deleteRoomUser(Long userUuid) {
        redisTemplate.delete(ROOM_USER.formatted(userUuid));
    }

    public List<UserDto> getRoomUserProfiles(Long RoomId) {
        Object roomObject = redisTemplate.opsForValue().get(ROOM.formatted(RoomId));
        RoomDto roomDto = objectMapper.convertValue(roomObject, RoomDto.class);
        return roomDto == null ? null : roomDto.users().stream().map(this::getUserProfile).toList();
    }

    public UserDto getUserProfile(Long userUuid) {
        Object o = redisTemplate.opsForValue().get(USER.formatted(userUuid));
        return o == null ? null : objectMapper.convertValue(o, UserDto.class);
    }

    public void cacheUserProfile(SessionUser user) {
        UserDto userDto = UserDto.builder().userId(user.userId()).name(user.name()).build();
        redisTemplate.opsForValue().set(USER.formatted(user.id()), userDto);
    }

}
