package com.roma.db.controller;

import com.roma.db.model.HotelClient;
import com.roma.db.model.Room;
import com.roma.db.model.dto.RegisterInfoDto;
import com.roma.db.service.ClientService;
import com.roma.db.service.RegisterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RegisterInfoController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private RegisterInfoService registerInfoService;

    @GetMapping("/make-contract/{id}")
    public String getMakeContract(@PathVariable String id, Model model) {

        model.addAttribute("roomId", id);

        return "make-contract";
    }

    @PostMapping("/make-contract/{roomId}")
    public String makeContract(@PathVariable String roomId,
                               @RequestParam String closingDate,
                               @RequestParam String description,
                               Model model) {
        HotelClient client = clientService.getClientByLogin(clientService.currentUser().getUsername());
        registerInfoService.initRegister(client, roomId, closingDate, description);
        return "redirect:/rooms/all";
    }
}
