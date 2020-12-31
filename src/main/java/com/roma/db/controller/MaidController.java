package com.roma.db.controller;

import com.roma.db.model.CleaningReport;
import com.roma.db.model.HotelClient;
import com.roma.db.model.Room;
import com.roma.db.service.CleaningReportService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MaidController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CleaningReportService cleaningReportService;

    @GetMapping("/maid/tasks")
    public String maidTasksPage(Model model,
        @RequestParam(name = "page", defaultValue = "1") Optional<Integer> page,
        @RequestParam(value = "size", defaultValue = "5") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        int nextPage = 0;
        int previousPage = 0;

        Page<Room> roomPage = roomService.findPaginatedUnclean(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("roomPage", roomPage);

        System.out.println(Arrays.toString(roomPage.getContent().toArray()));

        int totalPages = roomPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        if (clientService.currentUser()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("MAID"))) {
            return "maid-page";
        }
        else {
            return "redirect:/error";
        }

    }

    @GetMapping("/cleanRoom/{roomId}")
    public String cleanRoom(@PathVariable String roomId) {
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

        if (clientService.currentUser()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("MAID"))) {
            return "redirect:/maid/tasks";
        }
        else {
            return "redirect:/error";
        }
    }
}
