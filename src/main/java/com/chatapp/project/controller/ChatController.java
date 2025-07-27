package com.chatapp.project.controller;

import com.chatapp.project.form.request.user.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/chat.sendMessage")
    public void handleChatMessage(@Payload ChatMessage chatMessage, Principal principal) {
        // Đảm bảo sender là user đang kết nối (bảo mật)
        String senderUsername = principal.getName();
        chatMessage.setFrom(senderUsername);
        // Gửi tin nhắn tới user đích qua destination /user/{to}/queue/messages
        messagingTemplate.convertAndSend(
                "/topic/room." + chatMessage.getConversationId(),
                chatMessage
        );
    }
}
