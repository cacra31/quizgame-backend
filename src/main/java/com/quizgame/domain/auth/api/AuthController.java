package com.quizgame.domain.auth.api;


import com.quizgame.domain.auth.api.dto.LoginRequest;
import com.quizgame.domain.auth.api.dto.LoginResponse;
import com.quizgame.domain.auth.service.AuthService;
import com.quizgame.global.session.SessionRedisStore;
import com.quizgame.global.session.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.quizgame.global.session.SessionConst.LOGIN_USER;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SessionRedisStore sessionRedisStore;

    @PostMapping("")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        SessionUser sessionUser = SessionUser.from(authService.login(loginRequest));
        session.setAttribute(LOGIN_USER, sessionUser);
        sessionRedisStore.saveSession(session.getId(), sessionUser);
        return ResponseEntity.ok(new LoginResponse(sessionUser.userId(), sessionUser.name()));
    }

}
