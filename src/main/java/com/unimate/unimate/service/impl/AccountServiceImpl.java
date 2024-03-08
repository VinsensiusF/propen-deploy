package com.unimate.unimate.service.impl;

import com.unimate.unimate.entity.Account;
import com.unimate.unimate.exception.EntityNotFoundException;
import com.unimate.unimate.repository.AccountRepository;
import com.unimate.unimate.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(Account.class));
    }
}
