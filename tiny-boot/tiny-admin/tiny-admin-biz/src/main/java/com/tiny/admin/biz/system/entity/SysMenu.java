package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author baomidou
 * @since 2024-06-06
 */
@Getter
@Setter
@TableName("sys_menu")
@ApiModel(value = "SysMenu对象", description = "菜单权限表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("父级ID")
    private String parentId;

    @ApiModelProperty("菜单标题")
    private String name;

    @ApiModelProperty("路径")
    private String url;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("组件名称")
    private String componentName;

    @ApiModelProperty("一级菜单跳转地址")
    private String redirect;

    @ApiModelProperty("菜单类型(0:一级菜单;1:子菜单;2:按钮权限)")
    private Integer menuType;

    @ApiModelProperty("菜单权限编码")
    private String perms;

    @ApiModelProperty("权限策略(1:显示;2:禁用)")
    private String permType;

    @ApiModelProperty("菜单排序")
    private Integer sortNo;

    @ApiModelProperty("聚合子路由(0:否;1:是)")
    private Boolean alwaysShow;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("是否叶子节点(0:否;1:是)")
    private Boolean isLeaf;

    @ApiModelProperty("是否缓存该页面(0:否;1:是)")
    private Boolean keepAlive;

    @ApiModelProperty("是否隐藏路由(0:否;1:是)")
    private Integer hidden;

    @ApiModelProperty("是否隐藏 tab(0:否;1:是)")
    private Integer hiddenTab;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("删除状态(0:正常;1:已删除)")
    private Integer delFlag;

    @ApiModelProperty("按钮权限状态(0:有效;1:无效)")
    private String status;

    @ApiModelProperty("外链菜单打开方式(0:内部打开;1:外部打开)")
    private Boolean internalOrExternal;
}
