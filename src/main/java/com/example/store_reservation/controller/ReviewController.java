package com.example.store_reservation.controller;

import com.example.store_reservation.model.entity.Review;
import com.example.store_reservation.model.request.ReviewRequest;
import com.example.store_reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @PostMapping
    public ResponseEntity<?> writeReview(@RequestParam("storeName") String storeName, @RequestBody ReviewRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Review review = reviewService.writeReview(authentication, storeName, request);

        return ResponseEntity.ok().body(review);
    }

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteReview(@RequestParam("storeName") String storeName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Review review = reviewService.deleteReview(authentication, storeName);

        return ResponseEntity.ok().body(review);
    }

}
