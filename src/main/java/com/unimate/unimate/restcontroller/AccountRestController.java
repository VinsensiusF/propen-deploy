package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.CleanAccountDTO;
import com.unimate.unimate.dto.CreateAccountDTO;
import com.unimate.unimate.dto.EditUserAccountDTO;
import com.unimate.unimate.dto.ProfileImageDTO;
import com.unimate.unimate.dto.UpdateAccountDTO;
import com.unimate.unimate.dto.UpdatePasswordDTO;
import com.unimate.unimate.dto.mapper.AccountMapper;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.enums.AccountStatusEnum;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.exception.dto.ErrorResponseDTO;
import com.unimate.unimate.exception.dto.GeneralResponseDTO;
import com.unimate.unimate.repository.RoleRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.AuthenticationService;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountRestController {
    private final AccountService accountService;
    private final RoleRepository roleRepository;
    private static final String JWT_HEADER = "Authorization";

    @Autowired
    private AccountMapper accountMapper;
    private final AuthenticationService authenticationService;

    @Autowired
    public AccountRestController(AccountService accountService,
                                 RoleRepository roleRepository,
                                 AuthenticationService authenticationService
    ){
        this.accountService = accountService;
        this.roleRepository = roleRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/get-all")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccount());
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
        var passwordEncoder = new BCryptPasswordEncoder();

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
        account.setPassword(passwordEncoder.encode(body.getPassword()));
        account.setRole(roleRepository.findRoleByName(RoleEnum.valueOf(body.getRole())));
        account.setStatus(AccountStatusEnum.VERIFIED);

//        account.setProfilePicture(body.getProfilePicture());
//        account.setAddress(body.getAddress());
//        account.setPhoneNumber(body.getPhoneNumber());
//        account.setBirthday(body.getBirthday());
//        account.setJob(body.getJob());
//        account.setBio(body.getBio());
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

        Account updatedAccount = accountService.updateAccount(body);

        // this shouldn't return password
        CleanAccountDTO cleanAccount = accountMapper.accountToCleanAccountDTO(updatedAccount);
        return ResponseEntity.ok(cleanAccount);
    }

    @PutMapping("/edit")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<?> updateStudentAccount(@Valid @RequestBody EditUserAccountDTO editUserAccountDTO, HttpServletRequest request) {
        try {
            String requestToken = request.getHeader(JWT_HEADER).substring(7);
            Account account = accountService.getAccountFromJwt(requestToken);

            account.setName(editUserAccountDTO.getName());
            account.setBio(editUserAccountDTO.getBio());
            account.setBirthday(editUserAccountDTO.getBirthday());
            account.setJob(editUserAccountDTO.getJob());
            account.setPhoneNumber(editUserAccountDTO.getPhoneNumber());
            account.setAddress(editUserAccountDTO.getAddress());
            accountService.saveAccount(account);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to update profile");
        }

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

    @GetMapping("/get-logged-in-from-jwt")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public Account getAccountInfoFromToken(@RequestHeader("Authorization") String jwtToken) {
        // Extract Bearer token from Authorization header
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            String token = jwtToken.substring(7); // Extract token excluding "Bearer "
            // Decode and verify the token, extract user information as needed
            // For simplicity, let's just return the token content here
            return accountService.getAccountFromJwt(token);
        } else {
            return null;
        }
    }

    @PutMapping("/change-password")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<String> changePassword(
        @RequestBody UpdatePasswordDTO requestNewPassword, HttpServletRequest request
    ) {
        try {
            final String requestTokenHeader = request.getHeader(JWT_HEADER);
            var jwtToken = requestTokenHeader.substring(7);
            System.out.println(jwtToken);
            Account account = accountService.getAccountFromJwt(jwtToken);         
            accountService.changePasword(requestNewPassword, account);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to change password");
        }
    }
    
    @PutMapping("/change-profile-picture")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<Map> upload(ProfileImageDTO profileImageDTO, HttpServletRequest request) {
        try {
            String requestToken = request.getHeader(JWT_HEADER).substring(7);
            Account account = accountService.getAccountFromJwt(requestToken);
            profileImageDTO.setEmail(account.getEmail());
        return accountService.uploadImageProfile(profileImageDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Terjadi kesalahan dalam memproses permintaan."));
        }
    }



 
    @GetMapping("/teachers")
    public ResponseEntity<?> getAllTeachers() {
        List<Account> teachers = accountService.getAllTeachers();
        return ResponseEntity.ok().body(teachers);
    }
    
}
