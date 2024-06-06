package com.tiny.admin.biz.config;

import com.tiny.core.security.config.SecurityAuthConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by lxh at 2024-05-29 20:05:28
 */
@Configuration
public class AdminSecurityConfig extends SecurityAuthConfig {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername("admin")
                .password(super.passwordEncoder.encode("123456"))
                .roles("ADMIN", "USER")
                .build();
    }
}
