package com.tiny.core.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lxh at 2024-10-19 16:32:56
 */
@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperty {
    private String[] whiteUrlList;
}
