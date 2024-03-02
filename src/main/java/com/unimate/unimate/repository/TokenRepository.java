package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Token;
import com.unimate.unimate.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByTokenAndTokenType(UUID token, TokenType tokenType);
    Optional<Token> findTokenByAccountAndTokenTypeAndExpiredAtIsAfter(Account account, TokenType tokenType, Instant time);

}
