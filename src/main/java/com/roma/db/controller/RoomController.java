package com.roma.db.controller;

import com.roma.db.model.Room;
import com.roma.db.service.ClientService;
import com.roma.db.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    public RoomController( RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms/all")
    public String listRooms(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Optional<Integer> page,
            @RequestParam(value = "size", defaultValue = "5") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

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

    @GetMapping("rooms/{id}")
    public String getRooms(@PathVariable String id, Model model) {
        Room room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        return "room";
    }

    @PostMapping("/addroom")
    public String addRoom(Room room) {
        return "rooms";
    }
}