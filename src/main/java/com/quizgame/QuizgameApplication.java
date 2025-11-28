package com.quizgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QuizgameApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizgameApplication.class, args);
	}

}
