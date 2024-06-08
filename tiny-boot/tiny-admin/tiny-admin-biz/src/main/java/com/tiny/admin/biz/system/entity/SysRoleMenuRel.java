package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@Getter
@Setter
@TableName("sys_role_menu_rel")
@Schema(name = "SysRoleMenuRel", description = "角色菜单表")
public class SysRoleMenuRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "菜单ID")
    private String menuId;
}
