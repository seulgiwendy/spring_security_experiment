package com.example.security.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        Account account = new Account();
        Role role = new Role();
        account.setUserId("wheejuni101");
        role.setRoleName("ADMIN");

        account.setRoles(Arrays.asList(role));
        accountRepository.save(account);
    }


    @Test
    public void testInsert() {
        for (int i = 0; i < 100; i++) {
            Account account = new Account();

            account.setUserId("wheejuni" + i);
            account.setPassword("1234" + i);
            account.setName("박재성" + i);

            Role role = new Role();
            if (i < 80) {
                role.setRoleName("USER");
            } else if (i <= 90) {
                role.setRoleName("MANAGER");

            } else {
                role.setRoleName("ADMIN");
            }

            account.setRoles(Arrays.asList(role));

            accountRepository.save(account);

        }
    }

    @Test
    public void testRead() {
        assertNotNull(accountRepository.findByUserId("wheejuni101"));

    }

}