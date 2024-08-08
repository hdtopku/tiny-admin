package com.tiny.admin.biz.websocket.po;

import java.time.Instant;

public record Message(User user, String comment, Action action, Instant timestamp) {
}
