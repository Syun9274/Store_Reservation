package com.example.store_reservation.service;

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
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자(" + username + ")를 찾을 수 없습니다."));
    }

    private Store findStoreByName(String storeName) {
        return storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장(" + storeName + ")을 찾을 수 없습니다."));
    }

    @Transactional
    public Review writeReview(Authentication authentication, String storeName, ReviewRequest request) {
        User user = findUserByAuthentication(authentication);
        Store store = findStoreByName(storeName);

        boolean checkOutReservationExists = reservationRepository.existsByUserAndStoreAndReservationStatus(user, store, ReservationStatus.CHECKED_OUT);
        if (!checkOutReservationExists) {
            throw new IllegalArgumentException("리뷰 작성 가능한 예약이 없습니다.");
        }

        boolean reviewExists = reviewRepository.existsByUserAndStore(user, store);
        if (reviewExists) {
            throw new IllegalArgumentException("이미 해당 매장에 대한 리뷰를 작성하셨습니다.");
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
                .orElseThrow(() -> new IllegalArgumentException("해당 매장에 대한 작성된 리뷰가 없습니다."));

        reviewRepository.delete(review);
        return review;
    }
}

