package com.tiny.admin.biz.config;

import com.tiny.admin.biz.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Created by lxh at 2024-10-10 18:31:55
 */
@Configuration
public class i18nInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(requestInterceptor);
        // 指定拦截匹配模式
        interceptorRegistration.addPathPatterns("/system/**", "/sms/**", "/pms/**");
    }
}
