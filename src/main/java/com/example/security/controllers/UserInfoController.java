package com.example.security.controllers;

import com.example.security.model.Account;
import com.example.security.model.SecurityAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @GetMapping("/userinfo")
    public String getLoggedinAccount(Authentication authentication) {
        if (authentication.getPrincipal() instanceof  SecurityAccount) {
            SecurityAccount loginUser = (SecurityAccount)authentication.getPrincipal();
            log.info("login user's username is : {}", loginUser.getUsername());
            return "your name is " + loginUser.getAccount().getName() + ", your authority is " + loginUser.getAccount().getRoles().toString();
        }

//        return "your name is " + authentication.getPrincipal();
        return String.format("your name is %s, your other informations are as follows: %s", authentication.getPrincipal(), authentication.getDetails());

    }
}
