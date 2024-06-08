package com.tiny.admin.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiny.admin.biz.system.dto.SysMenuDTO;
import com.tiny.admin.biz.system.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenuDTO> menuTree();
}
