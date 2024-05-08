package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountById(Long id);

    Optional<Account> findAccountByEmail(String email);

    
    @Query("SELECT COUNT(e) FROM Account e")
    Long countAllAccount();
}
