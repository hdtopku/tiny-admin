package com.tiny.admin.biz.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiny.admin.biz.system.dto.SysMenuTree;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.mapper.SysMenuMapper;
import com.tiny.admin.biz.system.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenuTree> menuTree() {
        List<SysMenu> menuList = this.baseMapper.selectList(null);
        return convertTree(menuList);
    }

    public static List<SysMenuTree> convertTree(List<SysMenu> menuList) {
        List<SysMenuTree> menus = menuList.stream().map(menu -> BeanUtil.copyProperties(menu, SysMenuTree.class)).toList();
        List<SysMenuTree> parents = menus.stream().filter(item -> StringUtils.isBlank(item.getParentId())).toList();
        parents.forEach(item -> item.setChildren(dfs(menus, item.getId())));
        return parents;
    }

    private static List<SysMenuTree> dfs(List<SysMenuTree> menus, String parentId) {
        List<SysMenuTree> children = menus.stream().filter(item -> item.getParentId().equals(parentId)).toList();
        children.forEach(item -> item.setChildren(dfs(menus, item.getId())));
        return children;
    }


}
