package com.tiny.admin.biz.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiny.admin.biz.system.dto.SysMenuTree;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.mapper.SysMenuMapper;
import com.tiny.admin.biz.system.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@Service
@SuppressWarnings("all")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenuTree> menuTree(String keyword) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>();
        if(!StringUtils.isBlank(keyword)) {
            queryWrapper.like("name", keyword);
        }
        List<SysMenu> menuList = this.baseMapper.selectList(queryWrapper.orderByAsc("create_time"));
        return convertTree(menuList);
    }

    public static List<SysMenuTree> convertTree(List<SysMenu> menuList) {
        if (CollUtil.isEmpty(menuList)) return new ArrayList<>();
        Set<String> menuIds = new HashSet<>();
        List<SysMenuTree> menus = menuList.stream().filter(menu -> {
            if (menuIds.contains(menu.getId())) return false;
            menuIds.add(menu.getId());
            return true;
        }).sorted(Comparator.comparing(SysMenu::getSort)).map(menu -> {
            SysMenuTree sysMenuTree = BeanUtil.copyProperties(menu, SysMenuTree.class);
            sysMenuTree.setLabel(menu.getName());
            sysMenuTree.setIcon(menu.getIcon());
            return sysMenuTree;
        }).toList();
        List<SysMenuTree> parents = menus.stream().filter(item -> StringUtils.isBlank(item.getParentId())).toList();
        parents.forEach(item -> {
                    item.setChildren(dfs(menus, item.getId()));
                }
        );
        return parents;
    }

    private static List<SysMenuTree> dfs(List<SysMenuTree> menus, String parentId) {
        List<SysMenuTree> children = menus.stream().filter(item -> item.getParentId().equals(parentId)).toList();
        children.forEach(item -> item.setChildren(dfs(menus, item.getId())));
        if (CollUtil.isEmpty(children)) return null;
        return children;
    }
}
