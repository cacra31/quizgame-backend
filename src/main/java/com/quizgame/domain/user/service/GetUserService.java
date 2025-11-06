package com.quizgame.domain.user.service;

import com.quizgame.domain.user.domain.User;
import com.quizgame.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService {

    private final UserRepository userRepository;

    public User byId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    public User byUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

}
