package com.tiny.admin.biz.websocket.po;

public record Message(String fromUsername, String toUsername, String content) {
}
