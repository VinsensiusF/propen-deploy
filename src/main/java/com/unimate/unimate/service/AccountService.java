package com.unimate.unimate.service;

import com.unimate.unimate.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void saveAccount(Account account);

    Account updateAccount(Account account);

    void deleteAccount(Account account);

    List<Account> getAllAccount();

    Optional<Account> getAccountById(Long id);

    Optional<Account> getAccountByEmail(String email);

    Account getAccountFromJwt(String jwt);
}