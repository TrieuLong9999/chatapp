package com.chatapp.project.service;

import com.chatapp.project.entity.CustomUserDetails;
import com.chatapp.project.entity.RefreshToken;
import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.request.user.LoginRequest;
import com.chatapp.project.form.response.user.JwtResponse;
import com.chatapp.project.jwtconfig.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        // Tạo Refresh Token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUser().getId());

        return new JwtResponse(jwt, refreshToken.getToken());
    }
    public JwtResponse generateNewToken(UserEntity user){
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String jwt = jwtUtil.generateToken(userDetails);

        // Tạo Refresh Token

        return new JwtResponse(jwt);
    }
}
