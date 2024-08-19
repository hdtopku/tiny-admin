package com.tiny.admin.biz.websocket.controller;

import com.tiny.admin.biz.websocket.po.Message;
import com.tiny.admin.biz.websocket.service.MemberService;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Map;

/**
 * Created by lxh at 2024-08-03 21:57:02
 */
@Controller
@Log4j2
public class ChatController {
    @Resource
    private MemberService memberService;
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;
    private static final String GET_ONLINE_USERS_TOPIC = "/topic/onlineUsers";

    @MessageMapping("/getOnlineUsers")
    public void getOnlineUsers() {
        simpMessagingTemplate.convertAndSend(GET_ONLINE_USERS_TOPIC, memberService.getMembers());
    }

    @MessageMapping("/offline")
    public void offline(String userId, SimpMessageHeaderAccessor headerAccessor) {
        memberService.removeMember(userId);
        simpMessagingTemplate.convertAndSend(GET_ONLINE_USERS_TOPIC, memberService.getMembers());
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message message) {
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event) {
        log.info("WebSocket session is connecting ");
    }

    @EventListener
    public void handleSessionConnectedEvent(SessionConnectedEvent event) {
        log.info("WebSocket session connected ");
    }

    @EventListener
    private void handleSubscribeEvent(SessionSubscribeEvent event) {
        log.info("WebSocket session subscribed ");
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
//        log.info("WebSocket session disconnected:{} ", event.getUser().getName());
        log.info("WebSocket session disconnected ");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes == null) return;
        String username = (String) sessionAttributes.get("currentUser");
        if (username == null) return;
        memberService.removeMember(username);
        simpMessagingTemplate.convertAndSend("/topic/onlineUsers", memberService.getMembers());
//        Message newMessage = new Message(user.id(), user.username(), null, Action.LEFT, Instant.now());
//        simpMessagingTemplate.convertAndSend("/topic/messages", newMessage);
    }

}
