package com.unimate.unimate.service.impl;

import com.unimate.unimate.config.AuthConfigProperties;
import com.unimate.unimate.dto.ProfileImageDTO;
import com.unimate.unimate.dto.UpdateAccountDTO;
import com.unimate.unimate.dto.UpdatePasswordDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.exception.EntityNotFoundException;
import com.unimate.unimate.repository.AccountRepository;
import com.unimate.unimate.repository.RoleRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.CloudinaryService;
import com.unimate.unimate.util.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
  
    private final CloudinaryService cloudinaryService;
 

    private final AuthConfigProperties configProperties;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AuthConfigProperties configProperties, RoleRepository roleRepository, CloudinaryService cloudinaryService) {
        this.accountRepository = accountRepository;
        this.configProperties = configProperties;
        this.roleRepository = roleRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    // TODO implement updateAccount
    @Override
    public Account updateAccount(UpdateAccountDTO  accountRequest) {
        Optional<Account> optionalAccount = getAccountById(accountRequest.getId());
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Account account = optionalAccount.get();
        account.setName(accountRequest.getName());
        account.setEmail(accountRequest.getEmail());
        account.setRole(roleRepository.findRoleByName(RoleEnum.valueOf(accountRequest.getRole())));
//        account.setStatus(AccountStatusEnum.valueOf(accountRequest.getStatus()));
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
    public Account getAccountByIdUser(Long id) {
        return accountRepository.findAccountById(id).get();
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


     @Override
    public Account changePasword(UpdatePasswordDTO request, Account account) {
       var passwordEncoder = new BCryptPasswordEncoder();
       var user = accountRepository.findAccountByEmail(account.getEmail()).get();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return accountRepository.save(user);
    }

    @Override
    public Long getCountAccount() {
        return accountRepository.countAllAccount();
    }

    @Override
    public ResponseEntity<Map> uploadImageProfile(ProfileImageDTO profileImageDTO) {
        Account account = getAccountByEmail(profileImageDTO.getEmail()).get();
        try {
            if (account == null) {
                return ResponseEntity.badRequest().build();
            }
       
            String url = (cloudinaryService.uploadFile(profileImageDTO.getFile(), "profile"));
            if(url == null) {
                return ResponseEntity.badRequest().build();
            }
            account.setProfilePicture(url);
            accountRepository.save(account);
            return ResponseEntity.ok().body(Map.of("url", account.getProfilePicture()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }



}
