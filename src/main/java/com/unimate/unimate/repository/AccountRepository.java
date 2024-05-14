package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountById(Long id);

    Optional<Account> findAccountByEmail(String email);

    
    @Query("SELECT COUNT(e) FROM Account e")
    Long countAllAccount();

    @Query("SELECT a FROM Account a WHERE a.role.name = 'TEACHER'")
    List<Account> findAllTeachers();
}
