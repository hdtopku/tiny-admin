package com.tiny.admin.biz.system.controller;

import com.tiny.admin.biz.system.dto.UserInfo;
import com.tiny.admin.biz.system.service.AuthService;
import com.tiny.admin.biz.system.vo.LoginForm;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by lxh at 2024-05-31 23:54:34
 */
@RestController
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result<Map<String, Object>> login(@RequestBody LoginForm loginForm) {
        return Result.success(authService.login(loginForm.getUsername(), loginForm.getPassword()));
    }

    @GetMapping("/getSelfInfo")
    @Operation(summary = "获取用户信息")
    public Result<UserInfo> getInfo() {
        return Result.success(authService.getSelfInfo());
    }
}
