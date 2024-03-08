package com.unimate.unimate.restcontroller;

import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {
    private final AccountService accountService;
    @Autowired
    public AccountRestController(AccountService accountService){
        this.accountService = accountService;

    }

//    @GetMapping()
//    @ValidateToken(RoleEnum.STUDENT)
//    public ResponseEntity<AccountDTO> getAccount(){
//        return accountService.getAccount;
//    }
}
