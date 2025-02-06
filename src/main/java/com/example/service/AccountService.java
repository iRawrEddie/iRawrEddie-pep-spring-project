package com.example.service;

import com.example.entity.Account;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    /**
     * Login to an account
     * @param account Account to be retrieved from the database.
     * @return Account retrieved from database.
     */
    public Account getAccount(Account account) {
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(),
            account.getPassword());
    }
    
    /**
     * Persist an Account to the database.
     * @param account Account object to save to database.
     * @return The saved account retrieved from the database.
     */
    public Account persistAccount(Account account) {
        if (account.getUsername().equals("") ||
            account.getPassword().length() < 4)
            return null;
        return accountRepository.save(account);
    }
}
