package com.quizgame.global.session;

import com.quizgame.domain.user.domain.User;
import lombok.Builder;

@Builder
public record SessionUser(Long id, String userId, String name) {
    public static SessionUser from(User user) {
        return SessionUser.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .build();
    }
}