package com.example.store_reservation.controller;

import com.example.store_reservation.model.dto.StoreDTO;
import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.request.StoreRequest.AddStoreRequest;
import com.example.store_reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/store")
@RestController
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/create")
    public ResponseEntity<?> createStore(@RequestBody AddStoreRequest request) {
        Store store = storeService.saveStore(request);

        return ResponseEntity.ok().body(store);
    }

    @GetMapping("/search/all")
    public ResponseEntity<?> searchAllStores(final Pageable pageable) {
        Page<StoreDTO> storeList = storeService.getAllStores(pageable);

        return ResponseEntity.ok().body(storeList);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchStoreByKeyword(@RequestParam("keyword") String keyword, final Pageable pageable) {
        Page<StoreDTO> storeList = storeService.getStoreNameByKeyword(keyword, pageable);

        return ResponseEntity.ok().body(storeList);
    }

}
