package com.example.store_reservation.controller;

import com.example.store_reservation.model.request.AuthRequest;
import com.example.store_reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class AuthController {

    private final UserService userService;

    // 회원가입
    @PostMapping("signUp")
    public ResponseEntity<?> signUp(@RequestBody AuthRequest.SignUp request) {
        var result = userService.register(request);

        return ResponseEntity.ok(result);
    }

    // 로그인
    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest.SignIn request) {
        var user = userService.authenticate(request);
        var token = tokenProvider.generateToken(user.getUsername(), user.getRole());

        return ResponseEntity.ok(token);
    }




}
