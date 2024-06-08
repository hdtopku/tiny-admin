package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 菜单权限表
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 菜单标题
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * 组件
     */
    private String component;

    /**
     * 组件名称
     */
    private String componentName;

    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 菜单类型(0:一级菜单;1:子菜单;2:按钮权限)
     */
    private Integer menuType;

    /**
     * 菜单权限编码
     */
    private String perms;

    /**
     * 权限策略(1:显示;2:禁用)
     */
    private String permType;

    /**
     * 菜单排序
     */
    private Integer sortNo;

    /**
     * 聚合子路由(0:否;1:是)
     */
    private Integer alwaysShow;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否叶子节点(0:否;1:是)
     */
    private Integer isLeaf;

    /**
     * 是否缓存该页面(0:否;1:是)
     */
    private Integer keepAlive;

    /**
     * 是否隐藏路由(0:否;1:是)
     */
    private Integer hidden;

    /**
     * 是否隐藏 tab(0:否;1:是)
     */
    private Integer hiddenTab;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除状态(0:正常;1:已删除)
     */
    private Integer delFlag;

    /**
     * 按钮权限状态(0:有效;1:无效)
     */
    private String status;

    /**
     * 外链菜单打开方式(0:内部打开;1:外部打开)
     */
    private Integer internalOrExternal;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysMenu other = (SysMenu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getComponent() == null ? other.getComponent() == null : this.getComponent().equals(other.getComponent()))
            && (this.getComponentName() == null ? other.getComponentName() == null : this.getComponentName().equals(other.getComponentName()))
            && (this.getRedirect() == null ? other.getRedirect() == null : this.getRedirect().equals(other.getRedirect()))
            && (this.getMenuType() == null ? other.getMenuType() == null : this.getMenuType().equals(other.getMenuType()))
            && (this.getPerms() == null ? other.getPerms() == null : this.getPerms().equals(other.getPerms()))
            && (this.getPermType() == null ? other.getPermType() == null : this.getPermType().equals(other.getPermType()))
            && (this.getSortNo() == null ? other.getSortNo() == null : this.getSortNo().equals(other.getSortNo()))
            && (this.getAlwaysShow() == null ? other.getAlwaysShow() == null : this.getAlwaysShow().equals(other.getAlwaysShow()))
            && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
            && (this.getIsLeaf() == null ? other.getIsLeaf() == null : this.getIsLeaf().equals(other.getIsLeaf()))
            && (this.getKeepAlive() == null ? other.getKeepAlive() == null : this.getKeepAlive().equals(other.getKeepAlive()))
            && (this.getHidden() == null ? other.getHidden() == null : this.getHidden().equals(other.getHidden()))
            && (this.getHiddenTab() == null ? other.getHiddenTab() == null : this.getHiddenTab().equals(other.getHiddenTab()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getInternalOrExternal() == null ? other.getInternalOrExternal() == null : this.getInternalOrExternal().equals(other.getInternalOrExternal()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getComponent() == null) ? 0 : getComponent().hashCode());
        result = prime * result + ((getComponentName() == null) ? 0 : getComponentName().hashCode());
        result = prime * result + ((getRedirect() == null) ? 0 : getRedirect().hashCode());
        result = prime * result + ((getMenuType() == null) ? 0 : getMenuType().hashCode());
        result = prime * result + ((getPerms() == null) ? 0 : getPerms().hashCode());
        result = prime * result + ((getPermType() == null) ? 0 : getPermType().hashCode());
        result = prime * result + ((getSortNo() == null) ? 0 : getSortNo().hashCode());
        result = prime * result + ((getAlwaysShow() == null) ? 0 : getAlwaysShow().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getIsLeaf() == null) ? 0 : getIsLeaf().hashCode());
        result = prime * result + ((getKeepAlive() == null) ? 0 : getKeepAlive().hashCode());
        result = prime * result + ((getHidden() == null) ? 0 : getHidden().hashCode());
        result = prime * result + ((getHiddenTab() == null) ? 0 : getHiddenTab().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getInternalOrExternal() == null) ? 0 : getInternalOrExternal().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
        sb.append(", component=").append(component);
        sb.append(", componentName=").append(componentName);
        sb.append(", redirect=").append(redirect);
        sb.append(", menuType=").append(menuType);
        sb.append(", perms=").append(perms);
        sb.append(", permType=").append(permType);
        sb.append(", sortNo=").append(sortNo);
        sb.append(", alwaysShow=").append(alwaysShow);
        sb.append(", icon=").append(icon);
        sb.append(", isLeaf=").append(isLeaf);
        sb.append(", keepAlive=").append(keepAlive);
        sb.append(", hidden=").append(hidden);
        sb.append(", hiddenTab=").append(hiddenTab);
        sb.append(", description=").append(description);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", status=").append(status);
        sb.append(", internalOrExternal=").append(internalOrExternal);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}