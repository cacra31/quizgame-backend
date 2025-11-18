package com.quizgame.global.time;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TimeController {

    @GetMapping("/server-time")
    public ServerTimeResponse getServerTime() {
        return ServerTimeResponse.now();
    }
}