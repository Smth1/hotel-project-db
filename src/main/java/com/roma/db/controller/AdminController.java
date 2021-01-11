package com.roma.db.controller;

import com.roma.db.model.CleaningReport;
import com.roma.db.model.RegisterInfo;
import com.roma.db.model.Room;
import com.roma.db.service.AdminService;
import com.roma.db.service.ClientService;
import com.roma.db.service.RegisterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {
    private final RegisterInfoService registerInfoService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClientService clientService;

    @Autowired
    public AdminController(RegisterInfoService registerInfoService) {
        this.registerInfoService = registerInfoService;
    }

    @GetMapping("/admin/confirmRoom/{roomId}")
    public String confirmRoom(@PathVariable String roomId) {
        RegisterInfo byRoomId = registerInfoService.getByRoomId(roomId);
        byRoomId.setConfirmed(true);
        registerInfoService.save(byRoomId);

        return "redirect:/rooms/all";
    }

    @GetMapping("/admin/tasks")
    public String adminTasksPage(Model model,
                                @RequestParam(name = "page", defaultValue = "1") Optional<Integer> page,
                                @RequestParam(value = "size", defaultValue = "5") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        int nextPage = 0;
        int previousPage = 0;

        Page<CleaningReport> roomPage = adminService.findPaginatedUnclosed(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("reportPage", roomPage);

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
                .contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "admin-page";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/confirm/{reportId}")
    public String confirmReport(@PathVariable String reportId) {
        adminService.confirm(reportId);

        if (clientService.currentUser()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "redirect:/admin/tasks";
        } else {
            return "redirect:/error";
        }
    }
}
