package com.example.store_reservation.service;

import com.example.store_reservation.model.dto.ReservationDTO;
import com.example.store_reservation.model.entity.Reservation;
import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.entity.User;
import com.example.store_reservation.model.enums.ReservationStatus;
import com.example.store_reservation.repository.ReservationRepository;
import com.example.store_reservation.repository.StoreRepository;
import com.example.store_reservation.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        boolean reservationExists = reservationRepository.existsByUserAndStoreAndReservationStatus(user, store, ReservationStatus.CONFIRMED);
        if (reservationExists) {
            throw new IllegalArgumentException("해당 사용자(" + username + ")는 이미 매장(" + storeName + ")에 예약이 완료된 상태입니다.");
        }

        return reservationRepository.save(Reservation.builder()
                .user(user)
                .store(store)
                .reservationStatus(ReservationStatus.CONFIRMED)
                .build());
    }

    @Transactional
    public Reservation cancelReservation(Authentication authentication, String storeName) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자(" + username + ")를 찾을 수 없습니다."));

        Store store = storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장(" + storeName + ")을 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findByUserAndStoreAndReservationStatus(user, store, ReservationStatus.CONFIRMED)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자와 매장에 대한 예약을 찾을 수 없습니다."));

        reservation.setReservationStatus(ReservationStatus.CANCELED);
        return reservationRepository.save(reservation);
    }

    public Page<ReservationDTO> getAllReservations(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자(" + username + ")를 찾을 수 없습니다."));

        Page<Reservation> reservationPage = reservationRepository.findAllByUserAndReservationStatus(user, ReservationStatus.CONFIRMED, pageable);
        return reservationPage.map(reservation -> ReservationDTO.builder()
                .storeName(reservation.getStore().getStoreName())
                .username(reservation.getUser().getUsername())
                .status(reservation.getReservationStatus())
                .build());
    }
}
