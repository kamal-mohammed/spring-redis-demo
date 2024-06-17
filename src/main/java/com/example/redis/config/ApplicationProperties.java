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
    private int REDIS_PORT;

    @Value("${redis.secret}")
    private String REDIS_SECRET;

}
