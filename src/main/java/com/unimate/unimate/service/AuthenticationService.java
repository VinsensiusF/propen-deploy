package com.unimate.unimate.service;

import com.unimate.unimate.dto.*;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Token;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthenticationService {
    List<Account> findAll();
    String login(SignInDTO signInDTO);
    void initialSignUp(SignUpDTO signUpDTO);
    ResponseEntity<String> signUp(SignUpDTO signUpDTO);
    ResponseEntity<String> verifyEmail(VerificationRequestDTO verificationRequestDTO);
    Token forgotPassword(ForgotPasswordDTO forgotPasswordDTO);
    ResponseEntity<String> verifyForgotPassword(VerificationForgotPasswordDTO verificationForgotPasswordDTO);
    ResponseEntity<String> resendEmail(ResendEmailDTO resendEmailDTO);

}
