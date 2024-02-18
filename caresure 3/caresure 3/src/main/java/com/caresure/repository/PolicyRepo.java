package com.caresure.repository;

import com.caresure.pojo.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepo extends JpaRepository<Policy, Long> {
    Policy findByTitle(String title);
    Policy findById(int id);
}
