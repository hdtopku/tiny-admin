package com.tiny.core.security.config;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * Created by lxh at 2024-08-09 15:00:16
 */
public interface UserDetailsBo extends UserDetails {
    Set<String>  getTokens();
}
