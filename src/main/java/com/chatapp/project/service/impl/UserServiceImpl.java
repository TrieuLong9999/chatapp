package com.chatapp.project.service.impl;

import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.request.user.RegisterRequest;
import com.chatapp.project.form.response.user.UserView;
import com.chatapp.project.repository.UserRepository;
import com.chatapp.project.service.UserService;
import com.chatapp.project.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserEntity.Role.CLIENT); // mặc định role

        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> findUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public Optional<UserEntity> findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public Page<UserView> findAllUser(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return userRepository.findByUsernameContainingIgnoreCase(username, pageable);
    }

}
