package com.tiny.admin.biz.system.dto;

import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * Created by lxh at 2024-07-02 08:12:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleDto extends SysRole {
    private Set<SysMenu> menus;
}
