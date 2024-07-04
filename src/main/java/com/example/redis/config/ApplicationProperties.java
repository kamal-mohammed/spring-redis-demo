package com.example.redis.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationProperties {
    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.user}")
    private String redisUser;

    @Value("${redis.secret}")
    private String redisSecret;

    @Value("${redis.jedis.pool.max-total}")
    private int jedisPoolMaxTotal;

    @Value("${redis.jedis.pool.max-idle}")
    private int jedisPoolMaxIdle;

    @Value("${redis.jedis.pool.min-idle}")
    private int jedisPoolMinIdle;

}
