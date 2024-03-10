package com.unimate.unimate.restcontroller;

import com.unimate.unimate.dto.VerificationForgotPasswordDTO;
import com.unimate.unimate.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/verify")
public class VerificationRestController {
    private final AuthenticationService authenticationService;
    @Autowired
    public VerificationRestController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    @GetMapping("/email")
    public ResponseEntity<String> verifyEmail(@RequestParam UUID token) {
        return authenticationService.verifyEmail(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> verifyForgotPassword(@RequestBody VerificationForgotPasswordDTO verificationForgotPasswordDTO) {
        return authenticationService.verifyForgotPassword(verificationForgotPasswordDTO);
    }
}
