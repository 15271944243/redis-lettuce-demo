package com.redislettucedemo.config;

import io.lettuce.core.ReadFrom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisSocketConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
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
        //  To use a dedicated connection each time, set shareNativeConnection to false.
//        lettuceConnectionFactory.setShareNativeConnection();

        return lettuceConnectionFactory;
    }



    /**
     * Use Unix domain sockets to communicate with Redis
     * @return
     */
//    @Bean
    public LettuceConnectionFactory redisConnectionFactoryWithUnixDomainSocket() {
        RedisSocketConfiguration redisSocketConfiguration = new RedisSocketConfiguration("/var/run/redis.sock");
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisSocketConfiguration);
        return lettuceConnectionFactory;
    }

    /**
     * Write to Master, Read from Replica
     * @return
     */
//    @Bean
    public LettuceConnectionFactory redisConnectionFactoryForMasterReplica() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.SLAVE_PREFERRED)
                .build();
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration("server", 6379);
        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    /**
     * Redis Sentinel Support
     * @return
     */
//    @Bean
    public RedisConnectionFactory redisConnectionFactoryForSentinel() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("127.0.0.1", 26379)
                .sentinel("127.0.0.1", 26380);
        return new LettuceConnectionFactory(sentinelConfig);
    }
}
