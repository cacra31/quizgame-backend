package com.quizgame.domain.game.service;

import com.quizgame.domain.game.api.v1.dto.GameEvent;
import com.quizgame.global.code.GameStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.quizgame.global.constant.WebsocketTopic.ROOM_QUESTION_TOPIC;

@Service
@RequiredArgsConstructor
public class FinishGameService {

    public void execute() {

    }

}
