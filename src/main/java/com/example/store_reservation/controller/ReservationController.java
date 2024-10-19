package com.example.store_reservation.controller;

import com.example.store_reservation.model.entity.Reservation;
import com.example.store_reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/reservation")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> reservation(@RequestParam("storeName") String storeName) {
        Reservation reservation = reservationService.newReservation(storeName, user);

        return ResponseEntity.ok(reservation);
    }

}
