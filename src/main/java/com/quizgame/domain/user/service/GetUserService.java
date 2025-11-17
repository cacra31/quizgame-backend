package com.quizgame.domain.user.service;

import com.quizgame.domain.user.domain.User;
import com.quizgame.domain.user.repository.UserRepository;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.exception.QuizGameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService {

    private final UserRepository userRepository;

    public User byId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new QuizGameException(SystemMessageCode.NOT_FOUND));
    }

    public User byUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new QuizGameException(SystemMessageCode.NOT_FOUND));
    }

}
