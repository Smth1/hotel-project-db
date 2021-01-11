package com.roma.db.service;

import com.roma.db.model.CleaningReport;
import com.roma.db.model.HotelClient;
import com.roma.db.model.Room;
import com.roma.db.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaidService {
    private final RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private CleaningReportService cleaningReportService;

    @Autowired
    private ClientService clientService;

    @Autowired
    public MaidService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> unCleanRooms() {
        List<Room> rooms = this.roomRepository.findAll().stream()
                .filter(el -> !el.getClean())
                .collect(Collectors.toList());

        return rooms;
    }

    public Page<Room> findPaginatedUnclean(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Room> list;
        List<Room> rooms = this.unCleanRooms();

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

    public void cleanRoom(String roomId) {
        String username = clientService.currentUser().getUsername();
        HotelClient clientByLogin = clientService.getClientByLogin(username);

        Room room = roomService.getRoomById(roomId);
        room.setClean(true);
        roomService.saveRoom(room);

        CleaningReport cleaningReport = new CleaningReport();

        cleaningReport.setMaid(clientByLogin);
        cleaningReport.setClosed(false);
        cleaningReport.setCreationDate(LocalDateTime.now());
        cleaningReport.setDescription("some description");
        cleaningReport.setRooms(Collections.singletonList(room));

        cleaningReportService.makeCleaningReport(cleaningReport);
    }
}
