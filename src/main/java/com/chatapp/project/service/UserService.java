package com.chatapp.project.service;

import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.request.user.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity registerUser(RegisterRequest request);
    List<UserEntity> findUsersByIds(List<Long> ids);
    Optional<UserEntity> findByUsername(String userName);
}
