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

    private User findUserByAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자(" + username + ")를 찾을 수 없습니다."));
    }

    private Store findStoreByName(String storeName) {
        return storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장(" + storeName + ")을 찾을 수 없습니다."));
    }

    private Reservation checkReservation(Authentication authentication, String storeName, ReservationStatus status) {
        User user = findUserByAuthentication(authentication);
        Store store = findStoreByName(storeName);

        return reservationRepository.findByUserAndStoreAndReservationStatus(user, store, status)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자와 매장에 대한 예약을 찾을 수 없습니다."));
    }

    @Transactional
    public Reservation newReservation(Authentication authentication, String storeName) {
        User user = findUserByAuthentication(authentication);
        Store store = findStoreByName(storeName);

        boolean reservationExists = reservationRepository.existsByUserAndStoreAndReservationStatus(user, store, ReservationStatus.CONFIRMED);
        if (reservationExists) {
            throw new IllegalArgumentException("해당 사용자(" + user.getUsername() + ")는 이미 매장(" + storeName + ")에 예약이 완료된 상태입니다.");
        }

        return reservationRepository.save(Reservation.builder()
                .user(user)
                .store(store)
                .reservationStatus(ReservationStatus.CONFIRMED)
                .build());
    }

    @Transactional
    public Reservation cancelReservation(Authentication authentication, String storeName) {
        Reservation reservation = checkReservation(authentication, storeName, ReservationStatus.CONFIRMED);

        reservation.setReservationStatus(ReservationStatus.CANCELED);
        return reservationRepository.save(reservation);
    }

    public Page<ReservationDTO> getAllReservations(Authentication authentication, Pageable pageable) {
        User user = findUserByAuthentication(authentication);

        Page<Reservation> reservationPage = reservationRepository.findAllByUserAndReservationStatus(user, ReservationStatus.CONFIRMED, pageable);
        return reservationPage.map(reservation -> ReservationDTO.builder()
                .storeName(reservation.getStore().getStoreName())
                .username(reservation.getUser().getUsername())
                .status(reservation.getReservationStatus())
                .build());
    }

    @Transactional
    public Reservation checkInStore(Authentication authentication, String storeName) {
        Reservation reservation = checkReservation(authentication, storeName, ReservationStatus.CONFIRMED);

        reservation.setReservationStatus(ReservationStatus.CHECKED_IN);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation checkOutStore(Authentication authentication, String storeName) {
        Reservation reservation = checkReservation(authentication, storeName, ReservationStatus.CHECKED_IN);

        reservation.setReservationStatus(ReservationStatus.CHECKED_OUT);
        return reservationRepository.save(reservation);
    }
}
