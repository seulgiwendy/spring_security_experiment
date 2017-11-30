package com.example.security.configuration;

import com.example.security.model.Account;
import com.example.security.model.AccountRepository;
import com.example.security.model.SecurityAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class AccountDetailsService implements UserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(AccountDetailsService.class);
    private Account account;

    @Autowired
    private AccountRepository repo;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("requested user ID is : {}" , s);
        repo.findByUserId(s).ifPresent(a -> this.account = a);
        return repo.findByUserId(s).filter(a -> a != null).map(a -> new SecurityAccount(a)).orElseThrow(() -> new UsernameNotFoundException("no user matches your request!"));

//        return new User(s, "1111", Arrays.asList(new SimpleGrantedAuthority("MANAGER")));
    }

    public void saveNewAccountFromGitHub(Map<String, Object> map) {
        Account gitHubAccount = new Account();
        gitHubAccount.setName((String)map.get("name"));
        gitHubAccount.setUserId((String)map.get("login"));
        gitHubAccount.setPassword("1111");
        repo.save(gitHubAccount);
    }

    public Account getAccount() {
        return account;
    }
}
