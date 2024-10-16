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
        // TODO        if(userDetails == null)
//        ==>  Preparing: SELECT t.id,t.username,t.nickname,t.avatar,t.password,t.email,t.phone,t.status,t.del_flag,t.create_by,t.create_time,t.update_by,t.update_time,t2.id AS joina_id,t2.role_name,t2.description,t2.status AS joina_status,t2.create_by AS joina_create_by,t2.create_time AS joina_create_time,t2.update_by AS joina_update_by,t2.update_time AS joina_update_time,t4.id AS joinb_id,t4.parent_id,t4.name,t4.url,t4.component,t4.redirect,t4.type,t4.permission,t4.sort,t4.icon,t4.keep_alive,t4.hidden,t4.hidden_tab,t4.unauthorized_strategy,t4.description AS joina_description,t4.create_by AS joinb_create_by,t4.create_time AS joinb_create_time,t4.update_by AS joinb_update_by,t4.update_time AS joinb_update_time,t4.del_flag AS joina_del_flag,t4.status AS joinb_status,t4.internal_or_external FROM sys_user t LEFT JOIN sys_user_role_rel t1 ON (t1.user_id = t.id) LEFT JOIN sys_role t2 ON (t2.id = t1.role_id) LEFT JOIN sys_role_menu_rel t3 ON (t3.role_id = t2.id) LEFT JOIN sys_menu t4 ON (t4.id = t3.menu_id) WHERE (t.username = ? AND t.del_flag = ?) ORDER BY t4.sort ASC
//==> Parameters: anonymousUser(String), 1(Integer)
        userDetails.setPublicMenuList(sysMenuMapper.selectList(new LambdaQueryWrapper<>(SysMenu.class).eq(SysMenu::getUnauthorizedStrategy, 1)));
        return userDetails;
    }
}
