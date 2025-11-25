package com.quizgame.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisLockManager {

    private final StringRedisTemplate redisTemplate;

    public boolean lock(String key, long expireMillis) {
        return redisTemplate.opsForValue().setIfAbsent(key, "LOCKED", expireMillis, TimeUnit.MILLISECONDS);
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}