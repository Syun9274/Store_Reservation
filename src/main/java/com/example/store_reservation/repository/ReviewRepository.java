package com.example.store_reservation.repository;

import com.example.store_reservation.model.entity.Review;
import com.example.store_reservation.model.entity.Store;
import com.example.store_reservation.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByUserAndStore(User user, Store store);

    Optional<Review> findByUserAndStore(User user, Store store);
}
