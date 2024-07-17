package com.tiny.admin.biz.config.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

/**
 * Created by lxh at 2024-07-15 16:09:16
 */
@Component
public class AdminAccessDecisionManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Resource
    private ISysMenuService iSysMenuService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        String requestURI = object.getRequest().getRequestURI().toLowerCase();
        SysMenu sysMenu = iSysMenuService.getOne(new LambdaQueryWrapper<>(SysMenu.class).eq(SysMenu::getUrl, requestURI));
        if(sysMenu != null && StringUtils.hasText(sysMenu.getPermission())) {
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (GrantedAuthority authority : authentication.get().getAuthorities()) {
                if (antPathMatcher.match(authority.getAuthority(), requestURI)) {
                    return new AuthorizationDecision(true);
                }
            }
            return new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(true);
    }
}
