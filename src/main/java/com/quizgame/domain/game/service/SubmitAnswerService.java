package com.quizgame.domain.game.service;

import com.quizgame.domain.answer.api.v1.dto.AnswerRequest;
import com.quizgame.domain.question.api.v1.dto.QuestionDto;
import com.quizgame.domain.question.redis.QuestionRedisService;
import com.quizgame.global.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmitAnswerService {

    private final QuestionRedisService questionRedisService;

    public void execute(AnswerRequest request, SessionUser user){
        int index = request.index();
        List<QuestionDto> questions = questionRedisService.getQuestions(request.roomId());
        QuestionDto questionDto = questions.get(index);
        
    }

}
