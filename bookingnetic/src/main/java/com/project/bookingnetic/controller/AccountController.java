package com.project.bookingnetic.controller;


import com.project.bookingnetic.models.Account;
import com.project.bookingnetic.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(){
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @PostMapping("/create")
    public ResponseEntity<Account> saveAccount(@ModelAttribute("account") Account account){
        account.setDateCreated(LocalDate.parse("2022-12-12"));
        account.setRole("user");
        account.setAbout("about");
        account.setId(7l);
        return ResponseEntity.ok(accountService.saveAccount(account));
    }
}
