package com.roma.db.service;

import com.roma.db.model.HotelClient;
import com.roma.db.model.RegisterInfo;
import com.roma.db.repository.RegisterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class RegisterInfoService {
    private final RegisterInfoRepository registerInfoRepository;

    @Autowired
    public RegisterInfoService(RegisterInfoRepository registerInfoRepository) {
        this.registerInfoRepository = registerInfoRepository;
    }

    public void initRegister(HotelClient client, String roomId, String closingDate, String description) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDateTime dateTime = LocalDateTime.parse(closingDate, formatter);

        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setClient(client);
        registerInfo.setCreationDate(LocalDateTime.now());
        registerInfo.setClosingDate(dateTime);
        registerInfo.setDescription(description);
        registerInfo.setRoomId(roomId);

        registerInfoRepository.save(registerInfo);
    }

    public RegisterInfo getByRoomId(String roomId) {
        Optional<RegisterInfo> byRoomId = registerInfoRepository.findByRoomId(roomId);

        return byRoomId.orElse(null);

    }

    public void save(RegisterInfo byRoomId) {
        registerInfoRepository.save(byRoomId);
    }
}
