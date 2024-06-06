package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author baomidou
 * @since 2024-06-06
 */
@Getter
@Setter
@TableName("sys_role_menu_rel")
@ApiModel(value = "SysRoleMenuRel对象", description = "角色菜单表")
public class SysRoleMenuRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("菜单ID")
    private String menuId;
}
