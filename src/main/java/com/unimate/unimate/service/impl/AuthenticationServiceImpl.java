package com.unimate.unimate.service.impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.unimate.unimate.config.AuthConfigProperties;
import com.unimate.unimate.dto.*;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Role;
import com.unimate.unimate.entity.Token;
import com.unimate.unimate.enums.AccountStatusEnum;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.enums.TokenType;
import com.unimate.unimate.exception.*;
import com.unimate.unimate.repository.AccountRepository;
import com.unimate.unimate.repository.TokenRepository;
import com.unimate.unimate.repository.RoleRepository;
import com.unimate.unimate.service.AuthenticationService;
import com.unimate.unimate.service.EmailService;
import com.unimate.unimate.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    //todo autowird in constructor
    private final EmailService emailService;
    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final AuthConfigProperties configProperties;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationServiceImpl(EmailService emailService,
                                     AccountRepository accountRepository,
                                     TokenRepository tokenRepository,
                                     AuthConfigProperties configProperties,
                                     RoleRepository roleRepository) {
        this.emailService = emailService;
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.configProperties = configProperties;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public String login(SignInDTO signInDTO) {
        final Account account = accountRepository.findAccountByEmail(signInDTO.getEmail())
                .orElseThrow(AccountNotFoundException::new);

        //status need enum
        if(account.getStatus() == AccountStatusEnum.UNVERIFIED){
            throw new UnverifiedUserException();
        }
        if (!BCrypt.checkpw(signInDTO.getPassword(), account.getPassword())) {
            throw new InvalidPasswordException();
        }
        return "Bearer " + JwtUtility.jwtGenerator(account.getId(), configProperties.getSecret(), account.getRole().getName());
    }

    public ResponseEntity<String> signUp(SignUpDTO signUpDTO) {
        Optional<Account> existingAccount = accountRepository.findAccountByEmail(signUpDTO.getEmail());
        if (existingAccount.isPresent()) {
            throw new AccountExistedException();
        }
        final Account account = new Account();
        account.setRole(roleRepository.findRoleByName(RoleEnum.STUDENT));
        account.setEmail(signUpDTO.getEmail());
        account.setName(signUpDTO.getName());
        final String password = BCrypt.hashpw(signUpDTO.getPassword(), BCrypt.gensalt());
        account.setPassword(password);
        account.setStatus(AccountStatusEnum.UNVERIFIED);
        accountRepository.save(account);

        Token token = new Token();
        token.setToken(UUID.randomUUID());
        token.setAccount(account);
        token.setTokenType(TokenType.ACCOUNT_VERIFICATION);
        token.setIssuedAt(Instant.now());
        token.setExpiredAt(Instant.now().plus(15, ChronoUnit.MINUTES));
        tokenRepository.save(token);

        //ToDo implement email service
        HashMap<String, String> body = new HashMap<>();
        body.put("verificationlink", generateVerificationLink(token.getToken()));
        emailService.send(account.getEmail(), body);

        return ResponseEntity.ok("Account has been created. Check your email for verification.");
    }

    //todo AUTH CONFIG PROPERTIES
    private String generateVerificationLink(UUID token) {
        return "http://localhost:8080/api/verify/email?token=" + token.toString();
    }

    public ResponseEntity<String> verifyEmail(UUID id) {
        // Find the corresponding verification token
        Optional<Token> optionalToken = tokenRepository.findTokenByTokenAndTokenType(id, TokenType.ACCOUNT_VERIFICATION);

        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
        Token verificationToken = optionalToken.get();
        if (verificationToken.getExpiredAt().isBefore(Instant.now())) {
            //todo Token has expired & Handle expiration logic
            throw new InvalidTokenException();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
        Account account = verificationToken.getAccount();
        if (account.getStatus() == AccountStatusEnum.VERIFIED) {
            //todo User has already been activated & Redirect to login page
            throw new VerifiedUserException();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has already been verified. Redirecting to login page...");
        } else {
            // Mark account status as verified
            account.setStatus(AccountStatusEnum.VERIFIED);
            accountRepository.save(account);
            //todo Redirect to login page
            return ResponseEntity.ok("User has been successfully verified. Redirecting to login page...");
        }
    }

    public Token forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        // Find the corresponding verification token
        Optional<Account> existingAccount = accountRepository.findAccountByEmail(forgotPasswordDTO.getEmail());
        if (existingAccount.isEmpty()) {
            throw new AccountNotFoundException();
        }
        Account account = existingAccount.get();
        Token token = new Token();
        token.setToken(UUID.randomUUID());
        token.setAccount(account);
        token.setTokenType(TokenType.FORGOT_PASSWORD);
        token.setIssuedAt(Instant.now());
        token.setExpiredAt(Instant.now().plus(15, ChronoUnit.MINUTES));
        tokenRepository.save(token);

        HashMap<String, String> body = new HashMap<>();
        body.put("verificationlink", generateVerificationLink(token.getToken()));
        emailService.send(account.getEmail(), body);

        return token;
    }

    public ResponseEntity<String> verifyForgotPassword(VerificationForgotPasswordDTO verificationForgotPasswordDTO) {
        // Find the corresponding verification token
        Optional<Token> optionalToken = tokenRepository.findTokenByTokenAndTokenType(verificationForgotPasswordDTO.getToken(), TokenType.FORGOT_PASSWORD);

        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
        Token verificationToken = optionalToken.get();
        if (verificationToken.getExpiredAt().isBefore(Instant.now())) {
            throw new InvalidTokenException();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
        Account account = verificationToken.getAccount();
        if (account.getStatus() == AccountStatusEnum.UNVERIFIED) {
            throw new UnverifiedUserException();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account is not verified and can't change password");
        }
        String newPassword = verificationForgotPasswordDTO.getPassword();
        final String password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        account.setPassword(password);
        accountRepository.save(account);
        return ResponseEntity.ok("User's password has been changed. Redirecting to login page...");
    }

    public ResponseEntity<String> resendEmail(ResendEmailDTO resendEmailDTO){
        Optional<Account> optionalAccount = accountRepository.findAccountByEmail(resendEmailDTO.getEmail());
        if(optionalAccount.isEmpty()){
            throw new AccountNotFoundException();
            //return ResponseEntity.status(HttpStatus.OK).body("Account Not found.");
        }
        Account account = optionalAccount.get();
        if (account.getStatus() == AccountStatusEnum.VERIFIED) {
            return ResponseEntity.ok("Account has been verified. Redirecting to login page.");
        }

        Optional<Token> optionalToken = tokenRepository.findTokenByAccountAndTokenTypeAndExpiredAtIsAfter(account, TokenType.ACCOUNT_VERIFICATION, Instant.now());
        if(optionalToken.isPresent()){
            //Case where there is still active token, user needs to wait for delay.
            throw new EmailSentException();
            //return ResponseEntity.status(HttpStatus.OK).body("We have sent you the email before. If you don't receive your email, please try again after 15 minutes.");
        }

        Token token = new Token();
        token.setToken(UUID.randomUUID());
        token.setAccount(account);
        token.setTokenType(TokenType.ACCOUNT_VERIFICATION);
        token.setIssuedAt(Instant.now());
        token.setExpiredAt(Instant.now().plus(15, ChronoUnit.MINUTES));
        tokenRepository.save(token);

        HashMap<String, String> body = new HashMap<>();
        body.put("verificationlink", generateVerificationLink(token.getToken()));
        emailService.send(account.getEmail(), body);

        return ResponseEntity.ok("Email has been sent.");
    }

    @Override
    public List<Account> starter() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            for (RoleEnum roleName : RoleEnum.values()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
        if(findAll().isEmpty()){
            SignUpDTO signUpDTOAdmin = new SignUpDTO("admin@gmail.com", "admin", "admin");
            SignUpDTO signUpDTOTopLevel = new SignUpDTO("toplevel@gmail.com", "toplevel", "toplevel");
            SignUpDTO signUpDTOTeacher = new SignUpDTO("teacher@gmail.com", "teacher", "teacher");
            SignUpDTO signUpDTOCS = new SignUpDTO("cs@gmail.com", "cs", "cs");
            initialSignUp(signUpDTOAdmin);
            initialSignUp(signUpDTOTeacher);
            initialSignUp(signUpDTOTopLevel);
            initialSignUp(signUpDTOCS);
        }
        return accountRepository.findAll();
    }

    public void initialSignUp(SignUpDTO signUpDTO) {
        Optional<Account> existingAccount = accountRepository.findAccountByEmail(signUpDTO.getEmail());
        if (existingAccount.isPresent()) {
            throw new RuntimeException("Account has already existed");
        }
        final Account account = new Account();
        String name = signUpDTO.getName();
        switch (name) {
            case "teacher" -> account.setRole(roleRepository.findRoleByName(RoleEnum.TEACHER));
            case "admin" -> account.setRole(roleRepository.findRoleByName(RoleEnum.ADMIN));
            case "toplevel" -> account.setRole(roleRepository.findRoleByName(RoleEnum.TOP_LEVEL));
            case "cs" -> account.setRole(roleRepository.findRoleByName(RoleEnum.CUSTOMER_SERVICE));
        }

        account.setEmail(signUpDTO.getEmail());
        account.setName(signUpDTO.getName());
        final String password = BCrypt.hashpw(signUpDTO.getPassword(), BCrypt.gensalt());
        account.setPassword(password);
        account.setStatus(AccountStatusEnum.VERIFIED);
        accountRepository.save(account);
    }
}
