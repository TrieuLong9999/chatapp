package com.chatapp.project.service;

import com.chatapp.project.entity.RefreshToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
}
