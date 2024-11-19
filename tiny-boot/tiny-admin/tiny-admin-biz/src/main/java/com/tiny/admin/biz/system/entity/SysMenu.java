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
 * Menu Permissions Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-16
 */
@Getter
@Setter
@TableName("sys_menu")
@Schema(name = "SysMenu", description = "Menu Permissions Table")
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @Schema(description = "是否有该菜单的访问权限")
    private boolean unauthorized;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Parent ID")
    private String parentId;

    @Schema(description = "Menu Title")
    private String name;

    @Schema(description = "Path")
    private String url;

    @Schema(description = "Component")
    private String component;

    @Schema(description = "Redirect URL for Top-level Menu")
    private String redirect;

    @Schema(description = "Menu Type (1: Menu; 2: Button Permission)")
    private Integer type;

    @Schema(description = "Unauthorized Strategy: Button (0: Hide; 1: Show but not clickable; 2: Show and clickable but submission fails); Menu Redirect (0: 404 Page; 1: 403 Page)")
    private Integer unauthorizedStrategy;

    @Schema(description = "Menu Permission Code")
    private String permission;

    @Schema(description = "Menu Sort Order")
    private Integer sort;

    @Schema(description = "Menu Icon")
    private String icon;

    @Schema(description = "Cache Page (0: No; 1: Yes)")
    private Boolean keepAlive;

    @Schema(description = "Hide Route (0: No; 1: Yes)")
    private Boolean hidden;

    @Schema(description = "Hide Tab (0: No; 1: Yes)")
    private Integer hiddenTab;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Created By")
    private String createBy;

    @Schema(description = "Creation Time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Updated By")
    private String updateBy;

    @Schema(description = "Update Time")
    private LocalDateTime updateTime;

    @Schema(description = "Deletion Status (0: Active; 1: Deleted)")
    private Integer delFlag;

    @Schema(description = "Enabled Status (0: Disabled, 1: Enabled)")
    private Boolean enabled;

    @Schema(description = "External Link Opening Method (0: Internal; 1: External)")
    private Boolean internalOrExternal;
}
