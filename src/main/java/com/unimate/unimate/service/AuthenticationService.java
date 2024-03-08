package com.unimate.unimate.service;

import com.unimate.unimate.dto.*;
import com.unimate.unimate.entity.Token;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    String login(SignInDTO signInDTO);
    ResponseEntity<String> signUp(SignUpDTO signUpDTO);
    ResponseEntity<String> verifyEmail(VerificationRequestDTO verificationRequestDTO);
    Token forgotPassword(ForgotPasswordDTO forgotPasswordDTO);
    ResponseEntity<String> verifyForgotPassword(VerificationForgotPasswordDTO verificationForgotPasswordDTO);
    ResponseEntity<String> resendEmail(ResendEmailDTO resendEmailDTO);

}
