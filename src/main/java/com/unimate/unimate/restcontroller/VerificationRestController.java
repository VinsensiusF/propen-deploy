package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.VerificationChangeEmailDTO;
import com.unimate.unimate.dto.VerificationForgotPasswordDTO;
import com.unimate.unimate.enums.RoleEnum;
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
    @PostMapping("/email")
    public ResponseEntity<String> verifyEmail(@RequestParam UUID token) {
        return authenticationService.verifyEmail(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> verifyForgotPassword(@RequestBody VerificationForgotPasswordDTO verificationForgotPasswordDTO) {
        return authenticationService.verifyForgotPassword(verificationForgotPasswordDTO);
    }

    @PostMapping("/change-email")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<String> verifyChangeEmail(@RequestBody VerificationChangeEmailDTO verificationChangeEmailDTO) {
        return authenticationService.verifyChangeEmail(verificationChangeEmailDTO);
    }
}
