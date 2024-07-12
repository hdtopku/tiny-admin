package com.tiny.admin.biz.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.tiny.admin.biz.config.security.AdminUserDetails;
import com.tiny.admin.biz.system.dto.UserInfo;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.service.AuthService;
import com.tiny.core.redis.service.RedisService;
import com.tiny.core.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxh at 2024-06-10 08:42:23
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisService redisService;
    @Resource
    private UserDetailsService userDetailsService;
    private static final String TOKEN_KEY = "user-token";


    @Override
    public Map<String, Object> login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            AdminUserDetails sysUserDetails = (AdminUserDetails) authenticate.getPrincipal();
            Map<String, Object> map = new HashMap<>();
            String token = JwtTokenUtil.generateToken(MapUtil.of("username", sysUserDetails.getUsername()));
            map.put("token", token);
            UserInfo userInfo = BeanUtil.copyProperties(sysUserDetails, UserInfo.class);
            addUnauthorizedMenuList(sysUserDetails, userInfo);
            map.put("userInfo", userInfo);
            redisService.set(TOKEN_KEY + ":" + sysUserDetails.getUsername(), sysUserDetails);
            return map;
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new UsernameNotFoundException("用户名或密码错误");
        } catch (LockedException e) {
            throw new LockedException("用户已被锁定");
        } catch (DisabledException e) {
            throw new DisabledException("用户已被禁用");
        } catch (AccountExpiredException e) {
            throw new AccountExpiredException("用户账号已过期");
        } catch (CredentialsExpiredException e) {
            throw new CredentialsExpiredException("用户密码已过期");
        }
    }

    @Override
    public UserInfo getSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UsernameNotFoundException("未登录");
        }
        AdminUserDetails sysUserDetails = (AdminUserDetails) userDetailsService.loadUserByUsername(authentication.getName());
        UserInfo userInfo = BeanUtil.copyProperties(sysUserDetails, UserInfo.class);
        userInfo.setMenuTree(SysMenuServiceImpl.convertTree(sysUserDetails.getMenuList()));
        addUnauthorizedMenuList(sysUserDetails, userInfo);
        redisService.set(TOKEN_KEY + ":" + sysUserDetails.getUsername(), sysUserDetails);
        return userInfo;
    }

    private void addUnauthorizedMenuList(AdminUserDetails sysUserDetails, UserInfo userInfo) {
        userInfo.setPublicMenuList(sysUserDetails.getPublicMenuList());
        List<SysMenu> list = new ArrayList<>();
        for (SysMenu sysMenu : sysUserDetails.getPublicMenuList()) {
            if (sysMenu.getType() == 1) {
                sysMenu.setComponent(null);
                sysMenu.setUnauthorized(true);
                list.add(sysMenu);
            }
        }
        sysUserDetails.getMenuList().addAll(list);
        userInfo.setMenuTree(SysMenuServiceImpl.convertTree(sysUserDetails.getMenuList()));
    }
}
