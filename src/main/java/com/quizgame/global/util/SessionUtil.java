package com.quizgame.global.util;

import com.quizgame.global.session.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

/**
 * session Util
 * - Spring에서 제공하는 RequestContextHolder 를 이용하여
 * request 객체를 service까지 전달하지 않고 사용할 수 있게 해줌
 */
public class SessionUtil implements Serializable {

    public static final Duration SESSION_TTL = Duration.ofMinutes(30);
    public static final String LOGIN_USER = "LOGIN_USER";

    private SessionUtil() {
        throw new UnsupportedOperationException("SessionUtil class - cannot be instantiated");
    }

    /**
     * attribute 값을 가져 오기 위한 method
     *
     * @param name attribute key name
     * @return Object attribute obj
     */
    public static Object getAttribute(String name) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getAttribute(name, RequestAttributes.SCOPE_SESSION);
        }
        return null;
    }

    /**
     * attribute 설정 method
     *
     * @param name   attribute key name
     * @param object attribute obj
     */
    public static void setAttribute(String name, Object object) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
        } else {
            throw new IllegalStateException("No request context available. Cannot set session attribute: " + name);
        }
    }

    public static void removeAttribute(String name) {
        Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    public static HttpServletRequest fnGetHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        } else {
            return null;
        }
    }

    public static SessionUser getSessionUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (SessionUser) requestAttributes.getAttribute(LOGIN_USER, RequestAttributes.SCOPE_SESSION);
    }

}
