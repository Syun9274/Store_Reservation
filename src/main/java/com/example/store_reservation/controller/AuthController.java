package com.example.store_reservation.controller;

import com.example.store_reservation.model.request.AuthRequest;
import com.example.store_reservation.security.TokenProvider;
import com.example.store_reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    // 회원 가입
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody AuthRequest.SignUp request) {
        var result = userService.register(request);

        return ResponseEntity.ok(result);
    }

    // 로그인
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest.SignIn request) {
        var user = userService.authenticate(request);
        var token = tokenProvider.generateToken(user.getUsername(), user.getRoles());

        return ResponseEntity.ok(token);
    }




}
