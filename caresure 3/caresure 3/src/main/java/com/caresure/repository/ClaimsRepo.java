package com.caresure.repository;

import com.caresure.pojo.Claims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimsRepo extends JpaRepository<Claims, Long> {

    List<Claims> findByPolicyHolderUsername(String username);
}
