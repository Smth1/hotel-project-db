package com.roma.db.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String index(Model model) {
        model.addAttribute("name", "Goshik");
        return "index";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

//    @GetMapping("/client-profile")
//    public String clientProfile() {
//        return "client-profile";
//    }
}
