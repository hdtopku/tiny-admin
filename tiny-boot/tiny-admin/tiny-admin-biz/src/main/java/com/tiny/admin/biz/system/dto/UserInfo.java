package com.tiny.admin.biz.system.dto;

import com.tiny.admin.biz.system.entity.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * Created by lxh at 2024-06-12 16:04:14
 */
@Data
public class UserInfo {
    private String id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    private String phone;
    private List<SysMenuTree> menuTree;
    private List<SysMenu> publicMenuList;
}
