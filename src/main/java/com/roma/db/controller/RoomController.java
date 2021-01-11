package com.roma.db.controller;

import com.roma.db.model.Room;
import com.roma.db.model.dto.RoomDto;
import com.roma.db.service.ClientService;
import com.roma.db.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RoomController {
    private final RoomService roomService;
    private final ClientService clientService;

    @Autowired
    public RoomController(RoomService roomService, ClientService clientService) {
        this.roomService = roomService;
        this.clientService = clientService;
    }

    @GetMapping("/rooms/all")
    public String listRooms(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Optional<Integer> page,
            @RequestParam(value = "size", defaultValue = "5") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        int nextPage = 0;
        int previousPage = 0;

        Page<Room> roomPage = roomService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("roomPage", roomPage);

        System.out.println(Arrays.toString(roomPage.getContent().toArray()));

        int totalPages = roomPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "rooms";
    }

    @GetMapping("/rooms/{roomId}")
    public String getRooms(@PathVariable String roomId, Model model) {
        Room room = roomService.getRoomById(roomId);

        //room.

        model.addAttribute("room", room);
        return "room";
    }

    @GetMapping("/add-room")
    public String addRoomPage(Model model) {
        model.addAttribute("room", new RoomDto());
        if (clientService.currentUser()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "add-room";
        }
        else {
            return "redirect:/error";
        }
    }

    @PostMapping("/add-room")
    public String addRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setClean(false);
        room.setFloatNumber(roomDto.getFloatNumber());
        room.setNumber(roomDto.getNumber());

        roomService.saveRoom(room);

        return "redirect:/rooms/all";
    }

    @GetMapping("/rooms/delete/{roomId}")
    public String deleteRoom(@PathVariable String roomId) {

        if (clientService.currentUser()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ADMIN"))) {
            roomService.deleteRoom(roomId);
            return "redirect:/rooms/all";
        }
        else {
            return "redirect:/error";
        }
    }
}
