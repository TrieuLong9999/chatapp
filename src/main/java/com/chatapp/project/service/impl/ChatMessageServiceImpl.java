package com.chatapp.project.service.impl;

import com.chatapp.project.entity.ChatMessageEntity;
import com.chatapp.project.repository.ChatMessageRepository;
import com.chatapp.project.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Override
    public ChatMessageEntity save(ChatMessageEntity chatMessageEntity) {
        return chatMessageRepository.save(chatMessageEntity);
    }

    @Override
    public List<ChatMessageEntity> getMessagesBefore(String conversationId, String beforeTimestamp, Integer limit) {
        LocalDateTime time = beforeTimestamp != null ? LocalDateTime.parse(beforeTimestamp) : LocalDateTime.now();
        return chatMessageRepository.findByConversationIdAndTimestampBeforeOrderByTimestampDesc(
                conversationId,
                time,
                PageRequest.of(0, limit)
        );
    }
}
