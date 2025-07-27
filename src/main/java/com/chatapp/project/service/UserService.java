package com.chatapp.project.service;

import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.request.user.RegisterRequest;
import org.springframework.stereotype.Service;

public interface UserService {
    public UserEntity registerUser(RegisterRequest request);
}
