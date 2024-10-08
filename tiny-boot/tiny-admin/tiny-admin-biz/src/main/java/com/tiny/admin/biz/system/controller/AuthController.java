package com.tiny.admin.biz.system.controller;

import com.tiny.admin.biz.system.dto.UserInfo;
import com.tiny.admin.biz.system.entity.SysUser;
import com.tiny.admin.biz.system.service.AuthService;
import com.tiny.admin.biz.system.vo.LoginForm;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by lxh at 2024-05-31 23:54:34
 */
@RestController
@RequestMapping("/auth")
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
    public Result<UserInfo> getSelfInfo() {
        return Result.success(authService.getSelfInfo());
    }

    @PostMapping("/updateSelfInfo")
    @Operation(summary = "更新用户信息")
    public Result<Boolean> updateSelfInfo(@RequestBody SysUser sysUser) {
        return Result.success(authService.updateSelfInfo(sysUser));
    }
}
