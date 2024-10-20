package com.example.store_reservation.model.request;

import com.example.store_reservation.model.entity.User;
import com.example.store_reservation.model.enums.Authority;
import lombok.Data;

import java.util.List;

public class AuthRequest {

    @Data
    public static class SignUp {

        private String username;
        private String password;

        private List<Authority> roles;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .roles(roles)
                    .build();
        }
    }

    @Data
    public static class SignIn {
        private String username;
        private String password;
    }

}
