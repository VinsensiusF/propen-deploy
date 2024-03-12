package com.unimate.unimate.service.impl;

import com.unimate.unimate.config.AuthConfigProperties;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.exception.EntityNotFoundException;
import com.unimate.unimate.repository.AccountRepository;
import com.unimate.unimate.repository.TokenRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.util.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;

    private final AuthConfigProperties configProperties;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, TokenRepository tokenRepository, AuthConfigProperties configProperties) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.configProperties = configProperties;
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
            throw new EntityNotFoundException();
        }

        Account account = optionalAccount.get();
        account.setName(accountRequest.getName());
        account.setEmail(accountRequest.getEmail());
        account.setPassword(BCrypt.hashpw(accountRequest.getPassword(), BCrypt.gensalt()));
        account.setRole(accountRequest.getRole());
        account.setStatus(accountRequest.getStatus());
        account.setProfilePicture(accountRequest.getProfilePicture());
        account.setAddress(accountRequest.getAddress());
        account.setPhoneNumber(accountRequest.getPhoneNumber());
        account.setBirthday(accountRequest.getBirthday());
        account.setJob(accountRequest.getJob());
        account.setBio(accountRequest.getBio());
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

    @Override
    public Account getAccountFromJwt(String jwt) {
        Long accountId = JwtUtility.extractAccountId(jwt, configProperties.getSecret());
        return getAccountById(accountId).orElseThrow(EntityNotFoundException::new);
    }


}
