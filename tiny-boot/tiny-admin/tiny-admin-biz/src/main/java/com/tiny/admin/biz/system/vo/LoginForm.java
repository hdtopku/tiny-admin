package com.tiny.admin.biz.system.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Created by lxh at 2024-06-11 11:04:22
 */
@Data
public class LoginForm {
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
