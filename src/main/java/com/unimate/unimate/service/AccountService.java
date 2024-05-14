package com.unimate.unimate.service;

import com.unimate.unimate.dto.UpdatePasswordDTO;
import com.unimate.unimate.dto.ProfileImageDTO;
import com.unimate.unimate.dto.UpdateAccountDTO;
import com.unimate.unimate.entity.Account;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

public interface AccountService {

    void saveAccount(Account account);

    Account updateAccount(UpdateAccountDTO account);

    void deleteAccount(Account account);

    List<Account> getAllAccount();
    List<Account> getAllTeachers();
    Optional<Account> getAccountById(Long id);


    public Account getAccountByIdUser(Long id);
        

    Optional<Account> getAccountByEmail(String email);

    Account getAccountFromJwt(String jwt);

    Account changePasword(UpdatePasswordDTO changePasswordRequest, Account account);

    Long getCountAccount();

    public ResponseEntity<Map> uploadImageProfile(ProfileImageDTO profileImageDTO);
}