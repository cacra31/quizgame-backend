package com.quizgame.domain.user.api.v1;

import com.quizgame.domain.user.api.v1.dto.UserResponse;
import com.quizgame.domain.user.service.GetUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

     private final GetUserService getUserService;

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        return UserResponse.from(getUserService.byUserId(userId));
    }

}
