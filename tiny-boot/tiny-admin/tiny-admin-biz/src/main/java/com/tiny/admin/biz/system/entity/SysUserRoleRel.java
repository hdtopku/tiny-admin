package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author baomidou
 * @since 2024-06-06
 */
@Getter
@Setter
@TableName("sys_user_role_rel")
@ApiModel(value = "SysUserRoleRel对象", description = "用户角色关系表")
public class SysUserRoleRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("角色ID")
    private String roleId;
}
