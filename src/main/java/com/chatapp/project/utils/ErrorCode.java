package com.chatapp.project.utils;


public enum ErrorCode {
    OK(0, "success"),
    ERROR(1, "error"),
    INVALID_FORM(3, "invalid form"),
    TOKEN_ERROR(4, "token error"),
    USER_NOT_FOUND(1001, "User not found"),
    INVALID_OTP(1002, "Invalid OTP"),
    UNAUTHORIZED(1003, "Unauthorized access"),
    INVALID_CREDENTIALS(401, "Invalid username or password"),

    SERVER_ERROR(1004, "Server error");
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
