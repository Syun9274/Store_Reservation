package com.example.store_reservation.repository;

import com.example.store_reservation.model.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Page<Store> findAllByStoreNameContainingIgnoreCase(String keyword, Pageable pageable);

    Store findByStoreName(String storeName);
}

