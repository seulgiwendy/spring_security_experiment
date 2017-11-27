package com.example.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/loginForm")
    public String loginForm(HttpServletRequest request) {
        String referrer = request.getHeader("Referrer");
        request.getSession().setAttribute("prevPage", referrer);
        return "login.html";
    }
}
