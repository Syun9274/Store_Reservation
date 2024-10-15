package com.example.store_reservation.service;

import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.request.StoreRequest.AddStoreRequest;
import com.example.store_reservation.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Store saveStore(AddStoreRequest request) {
        Store store = Store.builder()
                .storeName(request.getStoreName())
                .storeAddress(request.getStoreAddress())
                .description(request.getDescription())
                .build();

        return storeRepository.save(store);
    }
}
