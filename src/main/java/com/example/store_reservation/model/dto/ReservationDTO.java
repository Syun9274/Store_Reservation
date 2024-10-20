package com.example.store_reservation.model.dto;

import com.example.store_reservation.model.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private String storeName;
    private String username;
    private ReservationStatus status;
}
