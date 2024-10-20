package com.example.store_reservation.service;

import com.example.store_reservation.exception.custom.UserException.NotExistUserException;
import com.example.store_reservation.model.entity.User;
import com.example.store_reservation.model.request.AuthRequest;
import com.example.store_reservation.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(NotExistUserException::new);
    }

    @Transactional
    public User register(AuthRequest.SignUp request) {
        boolean exists = userRepository.existsByUsername(request.getUsername());
        if (exists) {
            throw new RuntimeException();
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(request.toEntity());
    }

    public User authenticate(AuthRequest.SignIn request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(NotExistUserException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException();
        }

        return user;
    }
}
