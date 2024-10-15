package com.example.store_reservation.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class StoreRequest {

    @Getter
    @Setter
    public static class AddStoreRequest {
        private String storeName;
        private String storeAddress;
        private String description;
    }
}
