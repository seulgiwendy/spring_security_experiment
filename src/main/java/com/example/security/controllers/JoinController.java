package com.example.security.controllers;

import com.example.security.model.Account;
import com.example.security.model.AccountRepository;
import com.example.security.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class JoinController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String getJoinPage() {
        return "signup.html";
    }

    @GetMapping("/github")
    public String getGithub() {
        return null;
    }

    @PostMapping("/join")
    public String signUp(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRoles(Arrays.asList(new Role("MANAGER")));
        accountRepository.save(account);
        return ("redirect:/");
    }


}
