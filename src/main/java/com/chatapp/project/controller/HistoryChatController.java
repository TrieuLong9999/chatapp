package com.chatapp.project.controller;

import com.chatapp.project.entity.ChatMessageEntity;
import com.chatapp.project.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client/chat")
public class HistoryChatController {
    @Autowired
    private ChatMessageService chatMessageService;
    @GetMapping("/history/{conversationId}")
    public ResponseEntity<List<ChatMessageEntity>> getHistory(
            @PathVariable String conversationId,
            @RequestParam(required = false) String beforeTimestamp,  // lấy tin nhắn trước thời điểm này
            @RequestParam(defaultValue = "20") int limit
    ) {
        List<ChatMessageEntity> messages = chatMessageService.getMessagesBefore(conversationId, beforeTimestamp, limit);
        return ResponseEntity.ok(messages);
    }
}
