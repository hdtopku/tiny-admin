package com.tiny.admin.biz.system.dto;

import lombok.Data;

/**
* Created by lxh at 2024-06-14 18:48:07
*/
@Data
public class SysUserDto {
    private String key;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private Boolean status;
}