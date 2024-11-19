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
 * Table for storing user information
 * </p>
 *
 * @author lxh
 * @since 2024-11-18
 */
@Getter
@Setter
@TableName("sys_user")
@Schema(name = "SysUser", description = "Table for storing user information")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "User Nickname")
    private String nickname;

    @Schema(description = "Password")
    private String password;

    @Schema(description = "User Avatar URL")
    private String avatar;

    @Schema(description = "User Email Address")
    private String email;

    @Schema(description = "User Phone Number")
    private String phone;

    @Schema(description = "Account Status (0: Disabled; 1: Enabled)")
    private Boolean enabled;

    @Schema(description = "Delete Flag (0: Deleted; 1: Active)")
    private Boolean delFlag;

    @Schema(description = "Created By (User ID)")
    private String createBy;

    @Schema(description = "Creation Timestamp")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Updated By (User ID)")
    private String updateBy;

    @Schema(description = "Last Update Timestamp")
    private LocalDateTime updateTime;
}
