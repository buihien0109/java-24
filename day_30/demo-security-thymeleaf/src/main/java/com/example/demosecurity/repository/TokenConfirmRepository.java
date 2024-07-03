package com.example.demosecurity.repository;

import com.example.demosecurity.entity.TokenConfirm;
import com.example.demosecurity.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenConfirmRepository extends JpaRepository<TokenConfirm, Integer> {
    Optional<TokenConfirm> findByTokenAndType(String token, TokenType type);
}