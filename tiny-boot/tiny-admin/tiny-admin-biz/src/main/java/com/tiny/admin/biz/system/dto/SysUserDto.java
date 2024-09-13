package com.tiny.admin.biz.system.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
* Created by lxh at 2024-06-14 18:48:07
*/
@Data
public class SysUserDto {
    private String id;
    private String key;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    private String phone;
    private Boolean status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Set<String> roleNames = new HashSet<>();
}