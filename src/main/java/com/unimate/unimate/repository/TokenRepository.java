package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByToken(UUID token);
}
