package com.quizgame.global.interceptor;

import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.exception.QuizGameException;
import com.quizgame.global.session.SessionRedisService;
import com.quizgame.global.session.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.quizgame.global.util.SessionUtil.LOGIN_USER;


@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final SessionRedisService sessionRedisService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true; // 무조건 통과
        }

        HttpSession session = request.getSession(false);
        SessionUser sessionUser = null;

        if (session != null) {
            log.info("current sessionId = {}", session.getId());
            log.info("LOGIN_USER = {}", session.getAttribute(LOGIN_USER));
            sessionUser = (SessionUser) session.getAttribute(LOGIN_USER);
        }

        if (sessionUser == null) {
            throw new QuizGameException(SystemMessageCode.UNAUTHORIZED);
        }

        // Redis에도 세션이 존재하는지 확인
        String sessionId = session.getId();
        String redisValue = sessionRedisService.getRawSessionValue(sessionId);

        // Redis TTL 만료 = 자동 로그아웃
        if (redisValue == null) {
            session.invalidate(); // HttpSession 무효화
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        sessionRedisService.refreshTtl(sessionId);
        return true;
    }

}