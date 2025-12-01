package com.quizgame.domain.question.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizgame.domain.question.api.v1.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.quizgame.global.constant.RedisKey.QUESTION_KEY;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public List<QuestionDto> getQuestions(Long roomId) {
        Object o = redisTemplate.opsForValue().get(QUESTION_KEY.formatted(roomId));
        return o == null ? null : objectMapper.convertValue(o, new TypeReference<List<QuestionDto>>() {});
    }

    public void setQuestions(Long roomId, List<QuestionDto> questions) {
        redisTemplate.opsForValue().set(QUESTION_KEY.formatted(roomId), questions, 20, TimeUnit.MINUTES);
    }

    public void deleteQuestions(Long roomId) {
        redisTemplate.delete(QUESTION_KEY.formatted(roomId));
    }

}
