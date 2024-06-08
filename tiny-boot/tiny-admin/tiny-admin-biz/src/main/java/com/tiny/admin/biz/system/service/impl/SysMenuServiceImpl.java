package com.tiny.admin.biz.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiny.admin.biz.system.dto.SysMenuDTO;
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
    public List<SysMenuDTO> menuTree() {
        List<SysMenu> menuList = this.baseMapper.selectList(null);
        List<SysMenuDTO> res = menuList.stream().map(menu -> BeanUtil.copyProperties(menu, SysMenuDTO.class)).toList();
        return convertTree(res);
    }

    private List<SysMenuDTO> convertTree(List<SysMenuDTO> menus) {
        List<SysMenuDTO> parents = menus.stream().filter(item -> StringUtils.isBlank(item.getParentId())).toList();
        parents.forEach(item -> item.setChildren(dfs(menus, item.getId())));
        return parents;
    }

    private List<SysMenuDTO> dfs(List<SysMenuDTO> menus, String parentId) {
        List<SysMenuDTO> children = menus.stream().filter(item -> item.getParentId().equals(parentId)).toList();
        children.forEach(item -> item.setChildren(dfs(menus, item.getId())));
        return children;
    }
}
