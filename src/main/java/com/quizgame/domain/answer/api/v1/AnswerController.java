package com.quizgame.domain.answer.api.v1;

import com.quizgame.domain.answer.api.v1.dto.AnswerRequest;
import com.quizgame.domain.answer.service.SubmitAnswerService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.response.CommonResponse;
import com.quizgame.global.session.SessionUser;
import com.quizgame.global.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final SubmitAnswerService submitAnswerService;

    @PostMapping("/submit")
    public ResponseEntity<CommonResponse> answerSubmit(@RequestBody AnswerRequest request) {
        SessionUser sessionUser = SessionUtil.getSessionUser();
        submitAnswerService.execute(request, sessionUser);
        return ResponseEntity.ok(CommonResponse.from(SystemMessageCode.EMPTY_OK));
    }

}
