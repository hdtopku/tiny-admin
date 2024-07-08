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
 * 菜单权限表
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@Getter
@Setter
@TableName("sys_menu")
@Schema(name = "SysMenu", description = "菜单权限表")
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "父级ID")
    private String parentId;

    @Schema(description = "菜单标题")
    private String name;

    @Schema(description = "路径")
    private String url;

    @Schema(description = "组件")
    private String component;

    @Schema(description = "一级菜单跳转地址")
    private String redirect;

    @Schema(description = "菜单类型(0:一级菜单;1:子菜单;2:按钮权限)")
    private Integer type;

    @Schema(description = "菜单权限编码")
    private String permission;

    @Schema(description = "菜单排序")
    private Integer sort;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "是否缓存该页面(0:否;1:是)")
    private Boolean keepAlive;

    @Schema(description = "是否隐藏路由(0:否;1:是)")
    private Boolean hidden;

    @Schema(description = "是否隐藏 tab(0:否;1:是)")
    private Integer hiddenTab;

    @Schema(description = "按钮策略，未授权时(1:隐藏;2:显示但不可点击)")
    private Integer buttonStrategy;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private String updateBy;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "删除状态(0:正常;1:已删除)")
    private Integer delFlag;

    @Schema(description = "按钮权限状态(0:有效;1:无效)")
    private String status;

    @Schema(description = "外链菜单打开方式(0:内部打开;1:外部打开)")
    private Boolean internalOrExternal;
}
