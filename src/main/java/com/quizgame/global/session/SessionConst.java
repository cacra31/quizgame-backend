package com.quizgame.global.session;

import java.time.Duration;

public abstract class SessionConst {
    public static final Duration SESSION_TTL = Duration.ofMinutes(30);
    public static final String LOGIN_USER = "LOGIN_USER";
}
