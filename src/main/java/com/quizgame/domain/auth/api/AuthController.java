package com.quizgame.domain.auth.api;


import com.quizgame.domain.auth.api.dto.LoginRequest;
import com.quizgame.domain.auth.api.dto.LoginResponse;
import com.quizgame.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new LoginResponse(authService.login(loginRequest)));
    }

}
