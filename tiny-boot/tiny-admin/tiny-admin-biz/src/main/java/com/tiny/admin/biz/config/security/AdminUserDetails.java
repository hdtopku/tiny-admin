package com.tiny.admin.biz.config.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.entity.SysRole;
import com.tiny.admin.biz.system.entity.SysUser;
import com.tiny.core.security.config.UserDetailsBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by lxh at 2024-06-10 08:54:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUserDetails extends SysUser implements UserDetailsBo {

    @Serial
    private static final long serialVersionUID = 1L;
    private Set<String> tokens;
    private List<SysRole> roles=new ArrayList<>();
    private List<SysMenu> menuList=new ArrayList<>();
    private List<SysMenu> publicMenuList=new ArrayList<>(); // 没有权限，但是也可见的菜单列表

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return menuList.stream().filter(menu -> StringUtils.isNotBlank(menu.getUrl())).map(menu -> new SimpleGrantedAuthority(menu.getUrl().toLowerCase())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus();
    }
}
