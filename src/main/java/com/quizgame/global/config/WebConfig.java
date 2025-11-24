package com.quizgame.global.config;

import com.quizgame.global.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")              // 전체 API 기본 적용
                .excludePathPatterns(
                        "/auth/login",                   // 로그인 예외
                        "/auth/me",
                        "/api/v1/user/signup"     // 회원가입 예외
                );
    }
}