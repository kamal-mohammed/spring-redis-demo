package com.example.redis.config;

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

@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    ApplicationProperties properties;

    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();

        jedisClusterNodes.add(
                new HostAndPort(properties.getREDIS_HOST(),
                Integer.parseInt(properties.getREDIS_PORT())));

        HostAndPortMapper hpm = hostAndPort -> new HostAndPort(properties.getREDIS_HOST(),
                Integer.parseInt(properties.getREDIS_PORT()));

        /*DefaultJedisClientConfig jedisClientConfig = DefaultJedisClientConfig.builder()
                .hostAndPortMapper(hpm)
                .password(properties.getREDIS_SECRET())
                .build();*/

        return new JedisCluster(jedisClusterNodes,
                properties.getREDIS_USER(), properties.getREDIS_SECRET(),
                hpm);

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
