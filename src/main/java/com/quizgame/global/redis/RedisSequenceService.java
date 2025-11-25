package com.quizgame.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.quizgame.global.constant.GlobalConst.*;

@Component
@RequiredArgsConstructor
public class RedisSequenceService {

    private final RedisTemplate<String, Object> redisTemplate;

    public long nextRoomId() {
        return redisTemplate.opsForValue().increment(ROOM_SEQ_KEY);
    }

    public long nextGameId() {
        return redisTemplate.opsForValue().increment(GAME_SEQ_KEY);
    }

    public long nextQuestionId() {
        return redisTemplate.opsForValue().increment(QUESTION_SEQ_KEY);
    }

    public long nextAnswerId() {
        return redisTemplate.opsForValue().increment(ANSWER_SEQ_KEY);
    }
}