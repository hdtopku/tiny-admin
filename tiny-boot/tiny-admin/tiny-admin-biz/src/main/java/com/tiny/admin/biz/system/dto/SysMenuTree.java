package com.tiny.admin.biz.system.dto;

import com.tiny.admin.biz.system.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lxh at 2024-06-08 13:46:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuTree extends SysMenu {
    private String label;
    private String icon;
    private List<SysMenuTree> children;

    @Override
    public String getUrl() {
        if (StringUtils.hasText(super.getUrl()) && !super.getUrl().startsWith("/")) {
            return "/" + super.getUrl();
        }
        return super.getUrl();
    }
    @Override
    public String getComponent() {
        if (StringUtils.hasText(super.getComponent()) && !super.getComponent().startsWith("/")) {
            return "/" + super.getComponent();
        }
        return super.getComponent();
    }
}
