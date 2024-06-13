package com.tiny.admin.biz.system.dto;

import com.tiny.admin.biz.system.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by lxh at 2024-06-08 13:46:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuTree extends SysMenu {
    private String label;
    private String key;
    private String icon;
    private List<SysMenuTree> children;
}
