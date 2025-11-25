package com.quizgame.domain.auth.api;

import com.quizgame.domain.auth.api.dto.LoginRequest;
import com.quizgame.domain.auth.api.dto.LoginResponse;
import com.quizgame.domain.auth.service.AuthService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.exception.QuizGameException;
import com.quizgame.global.response.CommonResponse;
import com.quizgame.global.session.SessionRedisService;
import com.quizgame.global.session.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.quizgame.global.util.SessionUtil.LOGIN_USER;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SessionRedisService sessionRedisService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        SessionUser sessionUser = SessionUser.from(authService.login(loginRequest));
        session.setAttribute(LOGIN_USER, sessionUser);
        sessionRedisService.saveSession(session.getId(), sessionUser);
        return ResponseEntity.ok(new LoginResponse(sessionUser.userId(), sessionUser.name()));
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(HttpSession session) {
        SessionUser loginUser = (SessionUser) session.getAttribute(LOGIN_USER);
        if (loginUser == null) throw new QuizGameException(SystemMessageCode.UNAUTHORIZED);
        return ResponseEntity.ok(new LoginResponse(loginUser.userId(), loginUser.name()));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(HttpSession session) {
        if (session != null) {
            String sessionId = session.getId();
            sessionRedisService.deleteSession(sessionId);
            session.invalidate();
        }
        return ResponseEntity.ok(CommonResponse.from(SystemMessageCode.EMPTY_OK));
    }

}
