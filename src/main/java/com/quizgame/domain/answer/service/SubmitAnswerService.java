package com.quizgame.domain.answer.service;

import com.quizgame.domain.answer.api.v1.dto.AnswerRequest;
import com.quizgame.global.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmitAnswerService {

    public void execute(AnswerRequest request, SessionUser user){

    }

}
