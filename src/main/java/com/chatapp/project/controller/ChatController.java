package com.chatapp.project.controller;

import com.chatapp.project.entity.ChatMessageEntity;
import com.chatapp.project.form.request.user.ChatMessage;
import com.chatapp.project.service.ChatMessageService;
import com.chatapp.project.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageService chatMessageService;
    @MessageMapping("/chat.sendMessage")
    public void handleChatMessage(@Payload ChatMessage chatMessage, Principal principal) {
        // Đảm bảo sender là user đang kết nối (bảo mật)
        String senderUsername = principal.getName();
        chatMessage.setFrom(senderUsername);
        // save message
        if(!DataUtils.isEmpty(chatMessage.getConversationId())) {
            ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
            chatMessageEntity.setConversationId(chatMessage.getConversationId());
            chatMessageEntity.setContent(chatMessage.getContent());
            chatMessageEntity.setFromUser(senderUsername);
            chatMessageEntity.setTimestamp(LocalDateTime.now());
            chatMessageService.save(chatMessageEntity);
        }
        // Gửi tin nhắn tới user đích qua destination /user/{to}/queue/messages
        messagingTemplate.convertAndSend(
                "/topic/room." + chatMessage.getConversationId(),
                chatMessage
        );
    }
}
