package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Account;
import com.project.bookingnetic.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccounts(){
        return new ArrayList<>(accountRepository.findAll());
    }

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

}
