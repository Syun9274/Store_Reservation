package com.example.store_reservation.service;

import com.example.store_reservation.model.entity.Reservation;
import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.entity.User;
import com.example.store_reservation.model.enums.ReservationStatus;
import com.example.store_reservation.repository.ReservationRepository;
import com.example.store_reservation.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Reservation newReservation(String storeName, User user) {
        Store store = storeRepository.findByStoreName(storeName);

        Reservation reservation = Reservation.builder()
                .store(store)
                .user(user)
                .reservationStatus(ReservationStatus.CONFIRMED)
                .build();

        return reservationRepository.save(reservation);
    }
}
