package com.tiny.admin.biz.controller;

import com.tiny.core.redis.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lxh at 2024-05-29 15:01:37
 */
@RestController
public class HelloController {
    @Resource
    private RedisService redisService;
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String hello() {
        redisService.set("hello", "world");
        return "Hello, World!";
    }
}
