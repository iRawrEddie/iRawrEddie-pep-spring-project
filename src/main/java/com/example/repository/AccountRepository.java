package com.example.repository;

import com.example.entity.Account;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    Account findAccountByUsernameAndPassword(String username, String password);
}
