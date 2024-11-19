package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * User-Role Relationship Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-18
 */
@Getter
@Setter
@TableName("sys_user_role_rel")
@Schema(name = "SysUserRoleRel", description = "User-Role Relationship Table")
public class SysUserRoleRel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "User ID")
    private String userId;

    @Schema(description = "Role ID")
    private String roleId;
}
