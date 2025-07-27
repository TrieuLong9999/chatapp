package com.chatapp.project.service.impl;

import com.chatapp.project.entity.RefreshToken;
import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.repository.RefreshTokenRepository;
import com.chatapp.project.repository.UserRepository;
import com.chatapp.project.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${jwt.refresh}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).orElseThrow());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        return refreshTokenRepository.save(refreshToken);
    }
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
           return null;
        }
        return token;
    }
    public int deleteByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        return refreshTokenRepository.deleteByUser(user);
    }
}
