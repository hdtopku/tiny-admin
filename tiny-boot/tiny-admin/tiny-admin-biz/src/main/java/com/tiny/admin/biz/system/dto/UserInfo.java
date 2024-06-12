package com.tiny.admin.biz.system.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by lxh at 2024-06-12 16:04:14
 */
@Data
public class UserInfo {
    private String username;
    private String avatar;
    private String nickname;
    private List<SysMenuTree> menuTree;
}
