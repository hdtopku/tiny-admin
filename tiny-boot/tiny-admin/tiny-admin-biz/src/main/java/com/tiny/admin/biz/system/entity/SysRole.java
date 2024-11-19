package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Role Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-18
 */
@Getter
@Setter
@TableName("sys_role")
@Schema(name = "SysRole", description = "Role Table")
public class SysRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Role Name")
    private String roleName;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Created by")
    private String createBy;

    @Schema(description = "Creation Time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Updated by")
    private String updateBy;

    @Schema(description = "Update Time")
    private LocalDateTime updateTime;

    @Schema(description = "Role Status (0: Disabled; 1: Enabled)")
    private Boolean enabled;
}
