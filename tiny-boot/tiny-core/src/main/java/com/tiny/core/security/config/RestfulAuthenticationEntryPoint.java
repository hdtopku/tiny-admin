package com.tiny.core.security.config;

import cn.hutool.json.JSONUtil;
import com.tiny.core.web.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * The login has expired or not logged in, return 401 error.
 */
@Component
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println(JSONUtil.parse(Result.failure(HttpStatus.UNAUTHORIZED.value(), "未登录或登录已过期，请重新登录！")));
        response.getWriter().flush();
    }
}