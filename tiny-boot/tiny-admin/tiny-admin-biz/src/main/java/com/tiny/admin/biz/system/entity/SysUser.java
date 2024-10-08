package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@Getter
@Setter
@TableName("sys_user")
@Schema(name = "SysUser", description = "用户表")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    private String id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "电子邮件")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "状态(0:禁用;1:正常)")
    private Boolean status;

    @Schema(description = "删除状态(0:已删除;1:正常)")
    private Boolean delFlag;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private String updateBy;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
