package com.unimate.unimate.restcontroller;

import com.unimate.unimate.dto.VerificationForgotPasswordDTO;
import com.unimate.unimate.dto.VerificationRequestDTO;
import com.unimate.unimate.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verify")
public class VerificationRestController {
    private final AuthenticationService authenticationService;
    @Autowired
    public VerificationRestController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    @PostMapping("/email")
    public ResponseEntity<String> verifyEmail(@RequestBody VerificationRequestDTO verificationRequestDTO) {
        return authenticationService.verifyEmail(verificationRequestDTO);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> verifyForgotPassword(@RequestBody VerificationForgotPasswordDTO verificationForgotPasswordDTO) {
        return authenticationService.verifyForgotPassword(verificationForgotPasswordDTO);
    }
}
