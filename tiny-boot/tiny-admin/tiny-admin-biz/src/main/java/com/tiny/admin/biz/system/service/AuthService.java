package com.tiny.admin.biz.system.service;

import com.tiny.admin.biz.system.dto.UserInfo;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(String username, String password);

    UserInfo getSelfInfo();
}
