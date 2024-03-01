package com.unimate.unimate.restcontroller;

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
    @Autowired
    private AuthenticationService authenticationService;
    //TODO PISAHKAN ENDPOINT
    @PostMapping("/email")
    public ResponseEntity<String> verifyEmail(@RequestBody VerificationRequestDTO verificationRequestDTO) {
        return authenticationService.verifyEmail(verificationRequestDTO);
    }
}
