package com.example.store_reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @PostMapping
    public ResponseEntity<?> writeReview() {
        return null;
    }

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteReview() {
        return null;
    }

}
