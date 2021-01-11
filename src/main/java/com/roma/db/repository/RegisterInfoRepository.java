package com.roma.db.repository;

import com.roma.db.model.HotelClient;
import com.roma.db.model.RegisterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterInfoRepository extends JpaRepository<RegisterInfo, Integer> {
    Optional<RegisterInfo> findByRoomId(String roomId);
}
