package com.unimate.unimate.service;

import com.unimate.unimate.config.AuthConfigProperties;
import com.unimate.unimate.dto.SignInDTO;
import com.unimate.unimate.dto.SignUpDTO;
import com.unimate.unimate.dto.VerificationRequestDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.EmailVerificationToken;
import com.unimate.unimate.enums.AccountStatusEnum;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.repository.AccountRepository;
import com.unimate.unimate.repository.EmailVerificationTokenRepository;
import com.unimate.unimate.repository.RoleRepository;
import com.unimate.unimate.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
    //todo autowird in constructor
    private final EmailService emailService;
    private final AccountRepository accountRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final AuthConfigProperties configProperties;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationService( EmailService emailService,
                                  AccountRepository accountRepository,
                                  EmailVerificationTokenRepository emailVerificationTokenRepository,
                                  AuthConfigProperties configProperties,
                                  RoleRepository roleRepository) {
        this.emailService = emailService;
        this.accountRepository = accountRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.configProperties = configProperties;
        this.roleRepository = roleRepository;
    }


    public String login(SignInDTO signInDTO) {
        final Account account = accountRepository.findAccountByEmail(signInDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));

        //status need enum
        if(account.getStatus() == AccountStatusEnum.UNVERIFIED){
            throw new RuntimeException("Account not verified yet!");
        }
        if (!BCrypt.checkpw(signInDTO.getPassword(), account.getPassword())) {
            throw new RuntimeException("Password is not valid!");
        }
        return "Bearer " + JwtUtility.jwtGenerator(account.getId(), configProperties.getSecret());
    }

    public ResponseEntity<String> signUp(SignUpDTO signUpDTO) {
        Optional<Account> existingAccount = accountRepository.findAccountByEmail(signUpDTO.getEmail());
        if (existingAccount.isPresent()) {
            throw new RuntimeException("Account has already existed");
        }
        final Account account = new Account();
        account.setRole(roleRepository.findRoleByName(RoleEnum.STUDENT));
        account.setEmail(signUpDTO.getEmail());
        account.setName(signUpDTO.getName());
        final String password = BCrypt.hashpw(signUpDTO.getPassword(), BCrypt.gensalt());
        account.setPassword(password);
        account.setStatus(AccountStatusEnum.UNVERIFIED);
        accountRepository.save(account);

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(UUID.randomUUID());
        emailVerificationToken.setAccount(account);
        emailVerificationToken.setIssuedAt(Instant.now());
        emailVerificationToken.setExpiredAt(Instant.now().plus(30, ChronoUnit.MINUTES));
        emailVerificationTokenRepository.save(emailVerificationToken);

        //ToDo implement email service
        HashMap<String, String> body = new HashMap<>();
        body.put("verificationlink", generateVerificationLink(emailVerificationToken.getToken()));
        emailService.send(account.getEmail(), body);

        return ResponseEntity.ok("Account has been created. Check your email for verification.");
    }

    //todo AUTH CONFIG PROPERTIES
    private String generateVerificationLink(UUID token) {
        return "https://unimate.co.id/verify/email?token=" + token.toString();
    }

    public ResponseEntity<String> verifyEmail(VerificationRequestDTO verificationRequestDTO) {
        // Find the corresponding verification token
        Optional<EmailVerificationToken> optionalToken = emailVerificationTokenRepository.findEmailVerificationTokenByToken(verificationRequestDTO.getToken());

        if (optionalToken.isPresent()) {
            EmailVerificationToken verificationToken = optionalToken.get();
            if (verificationToken.getExpiredAt().isBefore(Instant.now())) {
                //todo Token has expired & Handle expiration logic
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
            }
            Account account = verificationToken.getAccount();
            if (account.getStatus() == AccountStatusEnum.VERIFIED) {
                //todo User has already been activated & Redirect to login page
                return ResponseEntity.ok("User has already been activated. Redirecting to login page...");
            } else {
                // Mark account status as verified
                account.setStatus(AccountStatusEnum.VERIFIED);
                accountRepository.save(account);
                //todo Redirect to login page
                return ResponseEntity.ok("User has been successfully verified. Redirecting to login page...");
            }

        } else {
            //TODO Token is invalid & Handle invalid token logic
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
    }

}
