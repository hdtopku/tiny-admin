package com.tiny.admin.biz.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import com.tiny.admin.biz.utils.LanguageUtil;

/**
 * Created by lxh at 2024-10-10 18:28:53
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的language
        String language = request.getHeader("language");
        if(!StringUtils.hasText(language)) {
            language = "CN";
        }
        LanguageUtil.setLanguage(language);
        return true;
    }
}
