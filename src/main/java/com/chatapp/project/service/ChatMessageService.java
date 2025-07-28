package com.chatapp.project.service;

import com.chatapp.project.entity.ChatMessageEntity;

import java.util.List;

public interface ChatMessageService {
    ChatMessageEntity save(ChatMessageEntity chatMessageEntity);
    List<ChatMessageEntity> getMessagesBefore(String conversationId, String beforeTimestamp, Integer limit);
}
