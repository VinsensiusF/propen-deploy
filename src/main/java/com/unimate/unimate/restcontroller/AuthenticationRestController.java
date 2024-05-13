package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.*;

import com.unimate.unimate.entity.Token;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationRestController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody SignInDTO signInDTO) {
    
        String token = authenticationService.login(signInDTO);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("token", token);

        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/signup")
    private ResponseEntity<String> signUp(@RequestBody Map<String, String> body) {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail(body.get("email"));
        signUpDTO.setPassword(body.get("password"));
        signUpDTO.setName(body.get("name"));

        return authenticationService.signUp(signUpDTO);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> body) {
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmail(body.get("email"));

        Token token = authenticationService.forgotPassword(forgotPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Password reset link has been sent. Check your email for verification.");
    }

    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestBody ChangeEmailDTO changeEmailDTO) {
        Token token = authenticationService.changeEmail(changeEmailDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Email change link has been sent. Check your email for verification.");
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendEmail(@RequestBody ResendEmailDTO resendEmailDTO){
        return authenticationService.resendEmail(resendEmailDTO);
    }

}
