package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@Getter
@Setter
@TableName("sys_user_role_rel")
@Schema(name = "SysUserRoleRel", description = "用户角色关系表")
public class SysUserRoleRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "角色ID")
    private String roleId;
}
