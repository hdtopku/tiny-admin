package com.tiny.core.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lxh at 2024-06-06 22:31:59
 */
@SuppressWarnings("ALL")
@Configuration
@MapperScan("com.tiny.**.mapper")
public class MybatisPlusCoreConfig {
}
