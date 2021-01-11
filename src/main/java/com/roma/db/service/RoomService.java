package com.roma.db.service;

import com.roma.db.model.Room;
import com.roma.db.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        List<Room> all = roomRepository.findAll();

        return all;
    }

    public Room getRoomById(String id) {
        Optional<Room> byId = roomRepository.findById(Integer.parseInt(id));

        return byId.orElse(null);
    }

    public void saveRoom(Room room) {
        this.roomRepository.save(room);
    }

    public Page<Room> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Room> list;
        List<Room> rooms = this.roomRepository.findAll();

        if (rooms.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, rooms.size());
            list = rooms.subList(startItem, toIndex);
        }

        Page<Room> roomPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), rooms.size());

        return roomPage;
    }

    public void deleteRoom(String roomId) {
        Optional<Room> byId = roomRepository.findById(Integer.parseInt(roomId));

        byId.ifPresent(roomRepository::delete);
    }
}
