package com.tiny.core.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lxh at 2024-08-09 14:09:31
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redisson.host}")
    private String host;

    @Value("${spring.redisson.port}")
    private  String port;

    @Value("${spring.redisson.password}")
    private String password;

    /**
     * 单机模式
     */
    @Bean
    public RedissonClient redissonSingle() {
        Config config = new Config();
        String address = "redis://" +host+":"+port;
        //设置
//        int timeout = 2000;
        config.setCodec(new FastjsonCodec())
                //这是用的集群server
                .useSingleServer()
                .setAddress(address)
//                .setTimeout(timeout)
                .setPassword(password);
        return Redisson.create(config);
    }
}
