package com.example.store_reservation.repository;

import com.example.store_reservation.model.entity.Reservation;
import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.entity.User;
import com.example.store_reservation.model.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByUserAndStoreAndReservationStatus(User user, Store store, ReservationStatus status);

    Page<Reservation> findAllByUserAndReservationStatus(User user, ReservationStatus status, Pageable pageable);

    boolean existsByUserAndStoreAndReservationStatus(User user, Store store, ReservationStatus status);
}
