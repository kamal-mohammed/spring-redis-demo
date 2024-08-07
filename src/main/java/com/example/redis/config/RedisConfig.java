package com.example.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.clients.jedis.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    ApplicationProperties properties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.addClusterNode(new RedisClusterNode(
                properties.getRedisHost(),
                Integer.parseInt(properties.getRedisPort())));
        redisClusterConfiguration.setPassword(properties.getRedisSecret());
        return new JedisConnectionFactory(redisClusterConfiguration);
    }

    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();

        jedisClusterNodes.add(
                new HostAndPort(properties.getRedisHost(),
                        Integer.parseInt(properties.getRedisPort())));

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(0);
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWait(Duration.ofSeconds(1));
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(1));

        log.info("Redis host: " + properties.getRedisHost());
        log.info("Redis port: " + properties.getRedisPort());
        log.info("Redis user: " + properties.getRedisUser());
        log.info("Redis secret: " + properties.getRedisSecret());

        return new JedisCluster(jedisClusterNodes, 5000, 5000, 3,
                properties.getRedisUser(), properties.getRedisSecret(),
                "spring-redis-demo", poolConfig);
    }
}
