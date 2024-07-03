package com.example.redis.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationProperties {
    @Value("${redis.host}")
    private String REDIS_HOST;

    @Value("${redis.port}")
    private String REDIS_PORT;

    @Value("${redis.user}")
    private String REDIS_USER;

    @Value("${redis.secret}")
    private String REDIS_SECRET;

    @Value("${redis.jedis.pool.max-total}")
    private int maxTotal;

    @Value("${redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${redis.jedis.pool.min-idle}")
    private int minIdle;

}
