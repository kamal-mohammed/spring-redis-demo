package com.example.redis.cache;

import com.example.redis.entity.StudentEntity;
import com.example.redis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CacheInitializer implements CommandLineRunner {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        System.out.println(studentRepository.save(new StudentEntity("1", "John", "Doe")));
        System.out.println("Redis Cache initialized!");
    }
}