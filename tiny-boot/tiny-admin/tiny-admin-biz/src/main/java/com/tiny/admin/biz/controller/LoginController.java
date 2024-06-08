package com.tiny.admin.biz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by lxh at 2024-05-31 23:54:34
 */
@Controller
@Tag(name = "登录模块")
public class LoginController {
    @GetMapping("/login")
    @Operation(summary = "登录页面")
    public String login() {
        return "login";
    }
}
