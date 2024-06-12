package com.tiny.admin.biz.config.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.entity.SysRole;
import com.tiny.admin.biz.system.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

/**
 * Created by lxh at 2024-06-10 08:54:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUserDetails extends SysUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    private List<SysRole> roles;
    private List<SysMenu> menuList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return menuList.stream().filter(menu -> StringUtils.isNotBlank(menu.getPerms())).map(menu -> new SimpleGrantedAuthority(menu.getPerms())).toList();
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
