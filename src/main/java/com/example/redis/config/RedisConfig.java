package com.example.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.*;

import java.time.Duration;
import java.util.Collections;
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
                properties.getREDIS_HOST(),
                Integer.parseInt(properties.getREDIS_PORT())));
        redisClusterConfiguration.setPassword(properties.getREDIS_SECRET());
        return new JedisConnectionFactory(redisClusterConfiguration);
    }

    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();

        jedisClusterNodes.add(
                new HostAndPort(properties.getREDIS_HOST(),
                Integer.parseInt(properties.getREDIS_PORT())));

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(30);
        poolConfig.setMaxWaitMillis(2000);

        log.debug("Redis host: " + properties.getREDIS_HOST());
        log.debug("Redis port: " + properties.getREDIS_PORT());
        log.debug("Redis user: " + properties.getREDIS_USER());
        log.debug("Redis secret: " + properties.getREDIS_SECRET());

        return new JedisCluster(jedisClusterNodes, 5000, 5000, 3,
                properties.getREDIS_USER(), properties.getREDIS_SECRET(),
                "educ-grad-trax-api", getConnectionPoolConfig());

        }

    private static ConnectionPoolConfig getConnectionPoolConfig() {
        ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
        connectionPoolConfig.setMaxTotal(8);
        connectionPoolConfig.setMaxIdle(8);
        connectionPoolConfig.setMinIdle(0);
        connectionPoolConfig.setBlockWhenExhausted(true);
        connectionPoolConfig.setMaxWait(Duration.ofSeconds(1));
        connectionPoolConfig.setTestWhileIdle(true);
        connectionPoolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(1));
        return connectionPoolConfig;
    }

}
