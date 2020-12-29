package com.roma.db.controller;

import com.roma.db.model.HotelClient;
import com.roma.db.model.Room;
import com.roma.db.model.dto.ClientLoginDto;
import com.roma.db.model.dto.HotelClientDto;
import com.roma.db.service.ClientService;
import com.roma.db.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClientController {
    private final ClientService clientService;
    private final RoomService roomService;

    @Autowired
    public ClientController(ClientService clientService, RoomService roomService) {
        this.clientService = clientService;
        this.roomService = roomService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/client-profile")
    public String getClientPage(Model model) {
        List<Room> rooms = roomService.getAllRooms();

        model.addAttribute("rooms", rooms);

        return "client-profile";
    }

    @GetMapping("/clients/all")
    public String getAllClients(Model model) {
        List<HotelClient> clients = clientService.getAllClients();

        model.addAttribute("clients", clients);

        return "clients";
    }
}
