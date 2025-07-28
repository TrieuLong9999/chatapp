package com.chatapp.project.socketconfig;

import com.chatapp.project.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Map;

@Component
public class WebSocketConnectionListener {
    @Autowired
    private RedisService redisService;

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        System.out.println("[Event] SessionConnectedEvent fired");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal user = event.getUser();
        if (user != null) {
            String username = user.getName();
            redisService.increaseConnection(username);
            System.out.println("[WS] " + username + " connected, connection count increased");
        } else {
            System.out.println("[WS] User principal is null");
        }
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        System.out.println("[Event] SessionConnectedEvent error");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttrs = accessor.getSessionAttributes();

        if (sessionAttrs != null) {
            String username = (String) sessionAttrs.get("username");
            if (username != null) {
                redisService.decreaseConnection(username);
                System.out.println("[WS] " + username + " disconnected, connection count decreased");
            }
        }
    }
}
