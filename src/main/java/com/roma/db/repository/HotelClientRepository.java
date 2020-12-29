package com.roma.db.repository;

import com.roma.db.model.HotelClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelClientRepository extends JpaRepository<HotelClient, Integer> {
    @Override
    Optional<HotelClient> findById(Integer integer);

    Optional<HotelClient> findByLogin(String login);
}
