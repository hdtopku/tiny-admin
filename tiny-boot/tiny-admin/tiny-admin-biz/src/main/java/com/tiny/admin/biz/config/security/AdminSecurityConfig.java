package com.tiny.admin.biz.config.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.system.entity.*;
import com.tiny.admin.biz.system.mapper.SysMenuMapper;
import com.tiny.admin.biz.system.mapper.SysUserMapper;
import com.tiny.core.security.config.SecurityAuthConfig;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by lxh at 2024-05-29 20:05:28
 */
@Configuration
public class AdminSecurityConfig extends SecurityAuthConfig<AdminUserDetails> {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MPJLambdaWrapper<SysUser> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(SysUser.class)
                .selectCollection(SysRole.class, AdminUserDetails::getRoles)
                .selectCollection(SysMenu.class, AdminUserDetails::getMenuList)
                .leftJoin(SysUserRoleRel.class, SysUserRoleRel::getUserId, SysUser::getId)
                .leftJoin(SysRole.class, SysRole::getId, SysUserRoleRel::getRoleId)
                .leftJoin(SysRoleMenuRel.class, SysRoleMenuRel::getRoleId, SysRole::getId)
                .leftJoin(SysMenu.class, SysMenu::getId, SysRoleMenuRel::getMenuId)
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getDelFlag, 1)
                .orderByAsc(SysMenu::getSort);
        sysMenuMapper.selectList(new LambdaQueryWrapper<>(SysMenu.class).eq(SysMenu::getUnauthorizedStrategy, 1));
        AdminUserDetails userDetails = sysUserMapper.selectJoinOne(AdminUserDetails.class, wrapper);
        if(userDetails == null) {
            throw new UsernameNotFoundException("Username or password error.");
        }
        userDetails.setPublicMenuList(sysMenuMapper.selectList(new LambdaQueryWrapper<>(SysMenu.class).eq(SysMenu::getUnauthorizedStrategy, 1)));
        return userDetails;
    }
}
