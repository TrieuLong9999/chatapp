package com.chatapp.project.service;

import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.request.user.RegisterRequest;
import com.chatapp.project.form.response.user.UserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity registerUser(RegisterRequest request);
    List<UserEntity> findUsersByIds(List<Long> ids);
    Optional<UserEntity> findByUsername(String userName);
    Page<UserView> findAllUser(String username, int page, int size);
}
