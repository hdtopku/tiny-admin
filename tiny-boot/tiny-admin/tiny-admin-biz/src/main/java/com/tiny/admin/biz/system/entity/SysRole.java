package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@Getter
@Setter
@TableName("sys_role")
@Schema(name = "SysRole", description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态(0:禁用;1:正常)")
    private Boolean status;

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
