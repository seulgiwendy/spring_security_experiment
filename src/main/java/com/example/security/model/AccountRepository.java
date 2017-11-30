package com.example.security.model;

import org.springframework.data.repository.CrudRepository;
import sun.util.resources.cldr.gv.LocaleNames_gv;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    public Optional<Account> findByUserId(String userId);

}
