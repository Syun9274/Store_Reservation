package com.example.store_reservation.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDTO {

    private String storeName;
    private String storeAddress;
    private String description;
}
