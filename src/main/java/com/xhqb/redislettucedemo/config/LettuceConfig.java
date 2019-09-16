package com.xhqb.redislettucedemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @description:
 * @author: xiaoxiaoxiang
 * @date: 2019/9/12 18:13
 */
@Configuration
public class LettuceConfig {
    private final String HOST_NAME = "redis-test1.o4r4dx.0001.cnn1.cache.amazonaws.com.cn";

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(HOST_NAME);
        // 设置密码
//        configuration.setPassword("xxxx");
        // 设计db
//        configuration.getDatabase(0)
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);
        return lettuceConnectionFactory;
    }
}
