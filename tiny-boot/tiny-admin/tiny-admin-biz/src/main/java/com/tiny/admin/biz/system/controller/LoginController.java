package com.tiny.admin.biz.system.controller;

import com.tiny.admin.biz.system.service.LoginService;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lxh at 2024-05-31 23:54:34
 */
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @GetMapping("/login")
    @Operation(summary = "登录接口")
    public Result<String> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        return Result.success(loginService.login(username, password));
    }
}
