package com.quizgame.domain.user.service;

import com.quizgame.domain.user.repository.UserRepository;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.exception.QuizGameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckDuplicateUserService {

    private final UserRepository userRepository;

    public void execute(String userId) {
        if(userRepository.existsByUserId(userId)){
            throw new QuizGameException(SystemMessageCode.USER_ID_DUPLICATE);
        }
    }
}
