package com.example.redis.cache;

import com.example.redis.entity.StudentEntity;
import com.example.redis.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPooled;

@Slf4j
@Component
public class CacheInitializer implements CommandLineRunner {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JedisCluster jedisCluster;

    @Override
    public void run(String... args) {
        jedisCluster.set("MY_KEY", "MYVALUE");
        System.out.println(studentRepository.save(new StudentEntity("1", "John", "Doe")));
        log.debug("Redis Cache initialized!");
        log.debug(jedisCluster.get("MY_KEY"));
    }
}