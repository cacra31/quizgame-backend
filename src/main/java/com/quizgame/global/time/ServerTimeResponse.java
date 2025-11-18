package com.quizgame.global.time;

import java.time.Instant;
import java.time.ZoneId;

public record ServerTimeResponse(
        long epochMillis,
        String zoneId
) {
    public static ServerTimeResponse now() {
        return new ServerTimeResponse(
                Instant.now().toEpochMilli(),
                ZoneId.systemDefault().getId()
        );
    }
}