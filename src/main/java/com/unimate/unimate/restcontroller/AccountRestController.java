package com.unimate.unimate.restcontroller;

import com.unimate.unimate.entity.Account;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    @Autowired
    public AccountRestController(AccountService accountService, AuthenticationService authenticationService){
        this.accountService = accountService;
        this.authenticationService = authenticationService;

    }

    @GetMapping("/starter")
    public List<Account> starter(){
        return authenticationService.findAll();
//        return authenticationService.starter();
    }

//    @GetMapping()
//    @ValidateToken(RoleEnum.STUDENT)
//    public ResponseEntity<AccountDTO> getAccount(){
//        return accountService.getAccount;
//    }
}
