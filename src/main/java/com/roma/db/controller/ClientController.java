package com.roma.db.controller;

import com.roma.db.model.HotelClient;
import com.roma.db.model.Role;
import com.roma.db.model.dto.ClientLoginDto;
import com.roma.db.model.dto.HotelClientDto;
import com.roma.db.service.ClientService;
import com.roma.db.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/clients/all")
    public String getAllClients(Model model,
                                @RequestParam(name = "page", defaultValue = "1") Optional<Integer> page,
                                @RequestParam(value = "size", defaultValue = "5") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<HotelClient> clientPage = clientService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("clientPage", clientPage);

        System.out.println(Arrays.toString(clientPage.getContent().toArray()));

        int totalPages = clientPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        Collection<? extends GrantedAuthority> authorities = clientService.currentUser().getAuthorities();

        System.out.println();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN")))
            return "clients";
        else
            return "redirect:/error";
    }

    @GetMapping("/clients/{id}")
    public String getClient(@PathVariable String id, HotelClientDto hotelClient, Model model) {
        HotelClient hotelClient1= this.clientService.getClientById(Integer.parseInt(id));

        hotelClient = new HotelClientDto(hotelClient1.getFirstName(), hotelClient1.getLastName(), hotelClient1.getLogin(), Role.USER);

        model.addAttribute("hotelClient", hotelClient);
        

        return "client";
    }

    @PostMapping("/clients/{id}")
    public String editClient(@PathVariable String id, HotelClientDto hotelClient, Model model) {
        clientService.saveClient(id, hotelClient);

        return "redirect:/";
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable String id) {

        if (clientService.currentUser()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ADMIN"))) {
            clientService.deleteClient(Integer.parseInt(id));

            return "redirect:/clients/all";
        }
        else
            return "redirect:/error";
    }

    @GetMapping("/editProfile")
    public String editProfile() {
        int id = clientService.
                getClientByLogin(clientService.currentUser().
                        getUsername()).
                getId();

        return "redirect:/clients/" + id;
    }
}
