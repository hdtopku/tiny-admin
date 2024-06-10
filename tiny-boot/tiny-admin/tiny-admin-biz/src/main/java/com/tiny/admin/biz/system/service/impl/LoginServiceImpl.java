package com.tiny.admin.biz.system.service.impl;

import cn.hutool.core.map.MapUtil;
import com.tiny.admin.biz.system.dto.SysUserDetails;
import com.tiny.admin.biz.system.service.LoginService;
import com.tiny.core.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by lxh at 2024-06-10 08:42:23
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            SysUserDetails sysUserDetails = (SysUserDetails) authenticate.getPrincipal();
            return JwtTokenUtil.generateToken(MapUtil.of("username", sysUserDetails.getUsername()));
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
}
