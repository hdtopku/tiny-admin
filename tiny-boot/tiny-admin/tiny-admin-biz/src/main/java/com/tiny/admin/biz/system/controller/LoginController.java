package com.tiny.admin.biz.system.controller;

import com.tiny.admin.biz.system.dto.LoginForm;
import com.tiny.admin.biz.system.service.LoginService;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lxh at 2024-05-31 23:54:34
 */
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result<String> login(@RequestBody LoginForm loginForm) {
        return Result.success(loginService.login(loginForm.getUsername(), loginForm.getPassword()));
    }
}
