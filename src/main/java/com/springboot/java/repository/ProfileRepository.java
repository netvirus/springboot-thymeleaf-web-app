package com.springboot.java.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EntityScan(basePackages = {"com.springboot.java.entity"})
public interface ProfileRepository extends CrudRepository<String, Integer> {
}
