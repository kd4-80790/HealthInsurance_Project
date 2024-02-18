package com.caresure.service;

import com.caresure.dto.PolicyDTO;
import com.caresure.pojo.Policy;

import java.util.List;
import java.util.Map;

public interface PolicyService {
    String addNewPolicy(PolicyDTO policyDTO);

    List<Policy> getAllPolicies();

    Map<String, Object> deletePolicyById(int id);

    Map<String, Object> updatePolicy(PolicyDTO policyDTO);

    Policy getPolicyById(Long policyId);

}
