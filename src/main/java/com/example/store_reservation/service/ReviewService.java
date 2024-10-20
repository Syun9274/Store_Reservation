package com.example.store_reservation.service;

import com.example.store_reservation.exception.custom.AuthException;
import com.example.store_reservation.exception.custom.ReservationException;
import com.example.store_reservation.exception.custom.ReviewException;
import com.example.store_reservation.model.entity.Review;
import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.entity.User;
import com.example.store_reservation.model.enums.ReservationStatus;
import com.example.store_reservation.model.request.ReviewRequest;
import com.example.store_reservation.repository.ReservationRepository;
import com.example.store_reservation.repository.ReviewRepository;
import com.example.store_reservation.repository.StoreRepository;
import com.example.store_reservation.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    private User findUserByAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(AuthException.NotMatchUserInfoException::new);
    }

    private Store findStoreByName(String storeName) {
        return storeRepository.findByStoreName(storeName)
                .orElseThrow(AuthException.NotMatchStoreInfoException::new);
    }

    @Transactional
    public Review writeReview(Authentication authentication, String storeName, ReviewRequest request) {
        User user = findUserByAuthentication(authentication);
        Store store = findStoreByName(storeName);

        boolean checkOutReservationExists = reservationRepository.existsByUserAndStoreAndReservationStatus(user, store, ReservationStatus.CHECKED_OUT);
        if (!checkOutReservationExists) {
            throw new ReservationException.NotFoundReservationException();
        }

        boolean reviewExists = reviewRepository.existsByUserAndStore(user, store);
        if (reviewExists) {
            throw new ReviewException.AlreadyExistsReviewException();
        }

        return reviewRepository.save(Review.builder()
                .user(user)
                .store(store)
                .content(request.getContents())
                .reviewDate(LocalDateTime.now())
                .build());
    }

    @Transactional
    public Review deleteReview(Authentication authentication, String storeName) {
        User user = findUserByAuthentication(authentication);
        Store store = findStoreByName(storeName);

        Review review = reviewRepository.findByUserAndStore(user, store)
                .orElseThrow(ReviewException.NotFoundReviewException::new);

        reviewRepository.delete(review);
        return review;
    }
}

