package com.chatapp.project.repository;

import com.chatapp.project.entity.ChatMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, String> {
    List<ChatMessageEntity> findByConversationIdAndTimestampBeforeOrderByTimestampDesc(
            String conversationId, LocalDateTime timestamp, Pageable pageable);
}
