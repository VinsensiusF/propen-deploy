package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.CleanAccountDTO;
import com.unimate.unimate.dto.CreateAccountDTO;
import com.unimate.unimate.dto.UpdateAccountDTO;
import com.unimate.unimate.dto.mapper.AccountMapper;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.enums.AccountStatusEnum;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.exception.dto.ErrorResponseDTO;
import com.unimate.unimate.exception.dto.GeneralResponseDTO;
import com.unimate.unimate.repository.RoleRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {
    private final AccountService accountService;
    private final RoleRepository roleRepository;

    private final AccountMapper accountMapper;

    private final AuthenticationService authenticationService;

    @Autowired
    public AccountRestController(AccountService accountService,
                                 RoleRepository roleRepository,
                                 AccountMapper accountMapper,
                                 AuthenticationService authenticationService
    ){
        this.accountService = accountService;
        this.roleRepository = roleRepository;
        this.accountMapper = accountMapper;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/find-by-email")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<?> findAccountByEmail(@RequestBody Map<String, String> body) {
        Optional<Account> existingAccount = accountService.getAccountByEmail(body.get("email"));
        if (existingAccount.isEmpty()) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setCode(HttpStatus.NOT_FOUND.value());
            errorResponseDTO.setMessage("Account not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        // this shouldn't return password
        Map<String,String> account = new HashMap<>();
        account.put("name", existingAccount.get().getName());
        account.put("email", existingAccount.get().getEmail());
        account.put("profilePicture", existingAccount.get().getProfilePicture());
        account.put("status", existingAccount.get().getStatus().name());
        account.put("role", existingAccount.get().getRole().getName().name());
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{id}")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<?> findAccountById(@PathVariable("id") Long id) {
        Optional<Account> existingAccount = accountService.getAccountById(id);
        if (existingAccount.isEmpty()) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setCode(HttpStatus.NOT_FOUND.value());
            errorResponseDTO.setMessage("Account not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        // this shouldn't return password
        CleanAccountDTO cleanAccount = accountMapper.accountToCleanAccountDTO(existingAccount.get());


        return ResponseEntity.ok(cleanAccount);
    }

    @PostMapping("/create")
    @ValidateToken({RoleEnum.ADMIN})
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountDTO body){

        Optional<Account> existingAccount = accountService.getAccountByEmail(body.getEmail());
        if (existingAccount.isPresent()) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setCode(HttpStatus.CONFLICT.value());
            errorResponseDTO.setMessage("Account with that email already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
        }

        Account account = new Account();
        account.setName(body.getName());
        account.setEmail(body.getEmail());
        account.setPassword(BCrypt.hashpw(body.getPassword(), BCrypt.gensalt()));
        account.setProfilePicture(body.getProfilePicture());
        account.setRole(roleRepository.findRoleByName(RoleEnum.valueOf(body.getRole())));
        account.setStatus(AccountStatusEnum.VERIFIED);
        accountService.saveAccount(account);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}")
    @ValidateToken({RoleEnum.ADMIN})
    public ResponseEntity<?> updateAccountById(@Valid @RequestBody UpdateAccountDTO body, @PathVariable("id") Long id) {
        Optional<Account> existingAccount = accountService.getAccountById(id);

        // checks if account isn't found
        if (existingAccount.isEmpty()) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setCode(HttpStatus.NOT_FOUND.value());
            errorResponseDTO.setMessage("Account not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        // checks if the new email has been used before
        //TODO

        Account accountToUpdate = new Account();
        accountToUpdate.setId(body.getId());
        accountToUpdate.setName(body.getName());
        accountToUpdate.setEmail(body.getEmail());
        accountToUpdate.setPassword(body.getPassword());
        accountToUpdate.setRole(roleRepository.findRoleByName(RoleEnum.valueOf(body.getRole())));
        accountToUpdate.setStatus(AccountStatusEnum.valueOf(body.getStatus()));
        accountToUpdate.setProfilePicture(body.getProfilePicture());


        Account updatedAccount = accountService.updateAccount(accountToUpdate);

        // this shouldn't return password
        CleanAccountDTO cleanAccount = accountMapper.accountToCleanAccountDTO(updatedAccount);
        return ResponseEntity.ok(cleanAccount);
    }


    @DeleteMapping("/{id}")
    @ValidateToken({RoleEnum.ADMIN})
    public ResponseEntity<?> deleteAccountById(@PathVariable("id") Long id) {
        Optional<Account> existingAccount = accountService.getAccountById(id);
        if (existingAccount.isEmpty()) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setCode(HttpStatus.NOT_FOUND.value());
            errorResponseDTO.setMessage("Account not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        String accountId = existingAccount.get().getId().toString();
        accountService.deleteAccount(existingAccount.get());

        GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
        generalResponseDTO.setCode(HttpStatus.OK.value());
        generalResponseDTO.setMessage(String.format("Account with ID %s has been successfully deleted", accountId));
        return ResponseEntity.ok(generalResponseDTO);
    }

    @GetMapping("/starter")
    public List<Account> starter(){
//        return authenticationService.findAll();
        return authenticationService.starter();

    }

//    @GetMapping()
//    @ValidateToken(RoleEnum.STUDENT)
//    public ResponseEntity<AccountDTO> getAccount(){
//        return accountService.getAccount;
//    }
}
