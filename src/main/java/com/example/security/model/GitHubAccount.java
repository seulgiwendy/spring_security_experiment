package com.example.security.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class GitHubAccount extends SecurityAccount{

    public GitHubAccount(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


}
