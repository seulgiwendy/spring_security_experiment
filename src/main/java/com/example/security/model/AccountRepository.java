package com.example.security.model;

import org.springframework.data.repository.CrudRepository;
import sun.util.resources.cldr.gv.LocaleNames_gv;

public interface AccountRepository extends CrudRepository<Account, Long> {

    public Account findByUserId(String userId);
}
