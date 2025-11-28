package com.quizgame.domain.user.api.v1;

import com.quizgame.domain.user.api.v1.dto.UserRequest;
import com.quizgame.domain.user.service.CreateUserService;
import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserService createUserService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> signup(@RequestBody UserRequest userRequest) {
        createUserService.execute(userRequest);
        return ResponseEntity.ok(CommonResponse.from(SystemMessageCode.SAVE_OK));
    }

}
