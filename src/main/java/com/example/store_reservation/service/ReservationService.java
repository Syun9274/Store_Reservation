package com.example.store_reservation.service;

import com.example.store_reservation.model.entity.Reservation;
import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.entity.User;
import com.example.store_reservation.model.enums.ReservationStatus;
import com.example.store_reservation.repository.ReservationRepository;
import com.example.store_reservation.repository.StoreRepository;
import com.example.store_reservation.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Reservation newReservation(Authentication authentication, String storeName) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자(" + username + ")를 찾을 수 없습니다."));

        Store store = storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장(" + storeName + ")을 찾을 수 없습니다."));

        return reservationRepository.save(Reservation.builder()
                .user(user)
                .store(store)
                .reservationStatus(ReservationStatus.CONFIRMED)
                .build());
    }
}
