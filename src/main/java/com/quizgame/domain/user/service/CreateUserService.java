package com.quizgame.domain.user.service;

import com.quizgame.domain.user.api.v1.dto.UserRequest;
import com.quizgame.domain.user.domain.User;
import com.quizgame.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;

    public void execute(UserRequest request) {
        userRepository.save(
                User.builder()
                        .userId(request.userId())
                        .password(request.password())
                        .name(request.name())
                        .type(request.type()).build()
        );
    }
}
