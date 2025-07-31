package com.chatapp.project.controller;

import com.chatapp.project.entity.RefreshToken;
import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.request.user.LoginRequest;
import com.chatapp.project.form.request.user.RegisterRequest;
import com.chatapp.project.form.request.user.TokenRefreshRequest;
import com.chatapp.project.form.response.user.JwtResponse;
import com.chatapp.project.service.AuthService;
import com.chatapp.project.service.RefreshTokenService;
import com.chatapp.project.service.UserService;
import com.chatapp.project.utils.BaseResponse;
import com.chatapp.project.utils.DataUtils;
import com.chatapp.project.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;
    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.authenticateUser(loginRequest);

            return BaseResponse.success(jwtResponse);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }

    @PostMapping("/register")
    public BaseResponse<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            UserEntity user = userService.registerUser(registerRequest);

            return BaseResponse.success(user);
        } catch (RuntimeException e) {
            return BaseResponse.error(ErrorCode.ERROR);
        }
    }
    @PostMapping("/refresh-token")
    public BaseResponse<?> refreshToken(@RequestBody TokenRefreshRequest request){
        String requestRefreshToken = request.getRefreshToken();

        if(DataUtils.isEmpty(requestRefreshToken)) return BaseResponse.error(ErrorCode.INVALID_FORM);

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken).orElse(null);

        if(refreshToken == null || refreshTokenService.verifyExpiration(refreshToken) == null) return BaseResponse.error(ErrorCode.TOKEN_ERROR);

        JwtResponse jwtResponse = authService.generateNewToken(refreshToken.getUser());
        jwtResponse.setRefreshToken(requestRefreshToken);
        return BaseResponse.success(jwtResponse);
    }
}
