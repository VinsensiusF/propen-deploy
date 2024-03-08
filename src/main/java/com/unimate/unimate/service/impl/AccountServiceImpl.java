package com.unimate.unimate.service.impl;


import com.unimate.unimate.entity.Account;
import com.unimate.unimate.exception.EntityNotFoundException;
import com.unimate.unimate.repository.AccountRepository;
import com.unimate.unimate.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    // TODO implement updateAccount
    @Override
    public Account updateAccount(Account accountRequest) {
        Optional<Account> optionalAccount = getAccountById(accountRequest.getId());
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException(Account.class);
        }

        Account account = optionalAccount.get();
        account.setName(accountRequest.getName());
        account.setEmail(accountRequest.getEmail());
        account.setPassword(BCrypt.hashpw(accountRequest.getPassword(), BCrypt.gensalt()));
        account.setRole(accountRequest.getRole());
        account.setStatus(accountRequest.getStatus());
        account.setProfilePicture(accountRequest.getProfilePicture());
        accountRepository.save(account);
        return account;
    }

    @Override
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }


}
