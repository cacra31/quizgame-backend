package com.quizgame.domain.user.api.v1;

import com.quizgame.domain.user.api.v1.dto.UserRequest;
import com.quizgame.domain.user.api.v1.dto.UserResponse;
import com.quizgame.domain.user.service.CreateUserService;
import com.quizgame.domain.user.service.GetUserService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final GetUserService getUserService;
    private final CreateUserService createUserService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(UserResponse.from(getUserService.byUserId(userId)));
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> register(@RequestBody UserRequest userRequest) {
        createUserService.execute(userRequest);
        return ResponseEntity.ok(CommonResponse.from(SystemMessageCode.SAVE_OK));
    }

}
