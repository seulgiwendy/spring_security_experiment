package com.example.security.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/admin")
    public String adminPage(HttpServletRequest request) {

        return "adminpage.html";
    }

    @GetMapping("/userp")
    public String usersPage() {
        return "userpage.html";
    }
}
