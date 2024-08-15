package com.tiny.admin.biz.websocket.po;

import java.time.Instant;

public record Message(String id, String username, String message, Action action, Instant timestamp) {
}
