package com.example.store_reservation.service;

import com.example.store_reservation.model.dto.StoreDTO;
import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.request.StoreRequest.AddStoreRequest;
import com.example.store_reservation.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Store saveStore(AddStoreRequest request) {
        Store store = modelMapper.map(request, Store.class);
        return storeRepository.save(store);
    }

    public Page<StoreDTO> getAllStores(Pageable pageable) {
        Page<Store> storePage = storeRepository.findAll(pageable);
        return storePage.map(store -> modelMapper.map(store, StoreDTO.class));
    }
}
