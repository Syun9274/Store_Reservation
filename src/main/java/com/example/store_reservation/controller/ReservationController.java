package com.example.store_reservation.controller;

import com.example.store_reservation.model.dto.ReservationDTO;
import com.example.store_reservation.model.entity.Reservation;
import com.example.store_reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/reservation")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @PostMapping
    public ResponseEntity<?> reservation(@RequestParam("storeName") String storeName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reservation reservation = reservationService.newReservation(authentication, storeName);

        return ResponseEntity.ok(reservation);
    }

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelReservation(@RequestParam("storeName") String storeName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reservation reservation = reservationService.cancelReservation(authentication, storeName);

        return ResponseEntity.ok(reservation);
    }

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @GetMapping("/showList")
    public ResponseEntity<?> showReservationList(final Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Page<ReservationDTO> reservationList = reservationService.getAllReservations(authentication, pageable);

        return ResponseEntity.ok(reservationList);
    }

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @PostMapping("/checkIn")
    public ResponseEntity<?> checkInReservation(@RequestParam("storeName") String storeName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reservation reservation = reservationService.checkInStore(authentication, storeName);

        return ResponseEntity.ok(reservation);
    }

    @PreAuthorize("hasAnyRole('USER', 'PARTNERSHIP')")
    @PostMapping("/checkOut")
    public ResponseEntity<?> checkOutReservation(@RequestParam("storeName") String storeName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reservation reservation = reservationService.checkOutStore(authentication, storeName);

        return ResponseEntity.ok(reservation);
    }
}
