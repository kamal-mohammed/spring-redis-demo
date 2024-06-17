package com.example.redis.repository;

import com.example.redis.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, String> {
	public static final String HASH_KEY = "School";
}
