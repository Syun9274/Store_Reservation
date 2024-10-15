package com.example.store_reservation.controller;

import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.request.StoreRequest.AddStoreRequest;
import com.example.store_reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/addStore")
    public ResponseEntity<?> addStore(@RequestBody AddStoreRequest request) {
        Store store = storeService.saveStore(request);

        return ResponseEntity.ok().body(store);
    }
}
