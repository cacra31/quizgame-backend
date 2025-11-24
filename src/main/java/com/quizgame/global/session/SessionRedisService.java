package com.quizgame.global.session;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.quizgame.global.session.SessionConst.SESSION_TTL;

@Component
@RequiredArgsConstructor
public class SessionRedisService {

    private final StringRedisTemplate redisTemplate;

    private String sessionKey(String sessionId) {
        return "quiz:session:" + sessionId;
    }

    // 로그인 시 세션 저장
    public void saveSession(String sessionId, SessionUser sessionUser) {
        String key = sessionKey(sessionId);

        // 일단 간단히 "id:userId:name" 형태로 직렬화
        String value = sessionUser.id() + ":" + sessionUser.userId() + ":" + sessionUser.name();

        // 예: 30분 TTL
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(30));
    }

    public String getRawSessionValue(String sessionId) {
        return redisTemplate.opsForValue().get(sessionKey(sessionId));
    }

    public void deleteSession(String sessionId) {
        redisTemplate.delete(sessionKey(sessionId));
    }

    public void refreshTtl(String sessionId) {
        String key = sessionKey(sessionId);
        Boolean exists = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(exists)) {
            // TTL만 갱신
            redisTemplate.expire(key, SESSION_TTL);
        }
    }
}
