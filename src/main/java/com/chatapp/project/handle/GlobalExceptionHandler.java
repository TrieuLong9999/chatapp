package com.chatapp.project.handle;

import com.chatapp.project.utils.BaseResponse;
import com.chatapp.project.utils.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(BaseResponse.error(errorCode));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<BaseResponse<Void>> handleNullPointer(NullPointerException ex) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error(errorCode));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<Void>> handleRuntime(NullPointerException ex) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error(errorCode));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(Exception ex) {
        ErrorCode errorCode = ErrorCode.ERROR;
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error(errorCode));
    }



}
