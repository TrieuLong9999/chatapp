package com.chatapp.project.form.request.user;

public class TokenRefreshRequest {
    private String refreshToken;

    public TokenRefreshRequest() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
