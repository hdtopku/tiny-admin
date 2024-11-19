package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Role-Menu Relationship Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-18
 */
@Getter
@Setter
@TableName("sys_role_menu_rel")
@Schema(name = "SysRoleMenuRel", description = "Role-Menu Relationship Table")
public class SysRoleMenuRel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Role ID")
    private String roleId;

    @Schema(description = "Menu ID")
    private String menuId;
}
