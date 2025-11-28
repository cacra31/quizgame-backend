package com.quizgame.domain.question.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizgame.domain.question.api.v1.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String QUESTION_KEY = "quiz:question:room:";

    public List<QuestionDto> getQuestions(Long roomId) {
        Object o = redisTemplate.opsForValue().get(QUESTION_KEY + roomId);
        return o == null ? null : objectMapper.convertValue(o, new TypeReference<List<QuestionDto>>() {});
    }

    public void setQuestions(Long roomId, List<QuestionDto> questions) {
        redisTemplate.opsForValue().set(QUESTION_KEY + roomId, questions);
    }

    public void deleteQuestions(Long roomId) {
        redisTemplate.delete(QUESTION_KEY + roomId);
    }

}
