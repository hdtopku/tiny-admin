package com.tiny.admin.biz.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.tiny.admin.biz.config.security.AdminUserDetails;
import com.tiny.admin.biz.system.dto.UserInfo;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.entity.SysUser;
import com.tiny.admin.biz.system.service.AuthService;
import com.tiny.admin.biz.system.service.ISysUserService;
import com.tiny.admin.biz.websocket.service.MemberService;
import com.tiny.core.redis.service.RedisService;
import com.tiny.core.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private RedissonClient redissonClient;
    @Resource
    private UserDetailsService userDetailsService;
    @Value("${constant.redis-users-token-map-key}")
    private String redisUsersTokenMapKey;
    @Resource
    private MemberService memberService;
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;
    @Resource
    private ISysUserService iSysUserService;

    private static final String GET_ONLINE_USERS_TOPIC = "/topic/onlineUsers";

    private static final String TOKEN_KEY = "user-token";


    @Override
    public Map<String, Object> login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            //  Authentication
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            AdminUserDetails sysUserDetails = (AdminUserDetails) authenticate.getPrincipal();

            // Generate and store token
            String token = JwtTokenUtil.generateToken(MapUtil.of("username", sysUserDetails.getUsername()));
            sysUserDetails.setTokens(new HashSet<>(Collections.singletonList(token)));
            sysUserDetails.setLoginTime(System.currentTimeMillis());
            RMapCache<String, Object> userMap = redissonClient.getMapCache(redisUsersTokenMapKey);
            userMap.put(sysUserDetails.getUsername(), sysUserDetails, 30, TimeUnit.MINUTES);

            // send online websocket message to client
            simpMessagingTemplate.convertAndSend(GET_ONLINE_USERS_TOPIC, memberService.getMembers());


            // Return result
            Map<String, Object> resMap = new HashMap<>();
            UserInfo userInfo = BeanUtil.copyProperties(sysUserDetails, UserInfo.class);
            addUnauthorizedMenuList(sysUserDetails, userInfo);  // 增加未授权，但在公共菜单中的菜单列表
            resMap.put("userInfo", userInfo);
            resMap.put("token", token);
            return resMap;
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new BadCredentialsException("用户名或密码错误");
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

    @Override
    public Boolean updateSelfInfo(SysUser sysUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UsernameNotFoundException("未登录");
        }
        AdminUserDetails sysUserDetails = (AdminUserDetails) userDetailsService.loadUserByUsername(authentication.getName());
        BeanUtil.copyProperties(sysUser, sysUserDetails);
        sysUser.setId(sysUserDetails.getId());
        sysUser.setPassword(null);
        sysUser.setCreateTime(null);
        sysUser.setDelFlag(null);
        sysUser.setStatus(null);
        return iSysUserService.updateById(sysUser);
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
