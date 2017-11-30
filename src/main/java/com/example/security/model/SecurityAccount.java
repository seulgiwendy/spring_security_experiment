package com.example.security.model;

import com.google.common.collect.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.print.DocFlavor;
import java.util.Collection;
import java.util.List;

public class SecurityAccount extends User{

    private static final String ROLE_PREFIX = "ROLE_";
    private Account account;

    public SecurityAccount(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityAccount(Account account) {
        super(account.getUserId(), account.getPassword(), makeAuthorities(account.getRoles()));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> makeAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = Lists.newArrayList();

        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + r.getRoleName()));
        });
        return authorities;
    }

    public Account getAccount() {
        return account;
    }
}
