package com.example.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ErrorPageController {

    private static final String ERROR_SUFFIX = "error.html";

    @GetMapping("/error/{code}")
    public String getErrorPage(@PathVariable String code) {
        StringBuilder sb = new StringBuilder();
        sb.append(code);
        sb.append(ERROR_SUFFIX);
        return sb.toString();
    }
}
