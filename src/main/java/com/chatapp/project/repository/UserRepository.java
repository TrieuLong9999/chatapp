package com.chatapp.project.repository;

import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.response.user.UserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String userName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Page<UserView> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
