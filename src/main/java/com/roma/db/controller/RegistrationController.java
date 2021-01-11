package com.roma.db.controller;

import com.roma.db.model.HotelClient;
import com.roma.db.model.Role;
import com.roma.db.model.dto.HotelClientDto;
import com.roma.db.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

@Controller
public class RegistrationController {
    private final ClientService clientService;

    @Autowired
    public RegistrationController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addClient(@ModelAttribute("client") HotelClientDto clientDto, Map<String, Object> model){
        HotelClient clientByLogin = clientService.getClientByLogin(clientDto.getLogin());

        HotelClient hotelClient = new HotelClient(clientDto.getFirstName(),
                clientDto.getLastName(),
                clientDto.getLogin(),
                clientDto.getPassword(),
                new HashSet<>(Collections.singletonList(Role.USER)));

        if (clientByLogin != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        clientService.saveClient(hotelClient);

        return "redirect:/login";
    }
}
