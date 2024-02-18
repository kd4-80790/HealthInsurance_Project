package com.caresure.service;

import com.caresure.dto.PolicyDTO;
import com.caresure.pojo.Policy;
import com.caresure.repository.PolicyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    private PolicyRepo policyRepo;
    @Override
    public String addNewPolicy(PolicyDTO policyDTO) {
        Policy policy= policyRepo.findByTitle(policyDTO.getTitle());
        if(policy==null){
            policy=new Policy();
            policy.setTitle(policyDTO.getTitle());
            policy.setDescription(policyDTO.getDescription());
            policyRepo.save(policy);
            return "New policy added";
        }else {
            return "policy with same title already exists";
        }
    }

    @Override
    public List<Policy> getAllPolicies() {
        return policyRepo.findAll();
    }

    @Override
    public Map<String, Object> deletePolicyById(int id) {
        Map<String, Object> response=new HashMap<>();
//        Policy policy= policyRepo.findByTitle(title);
        Policy policy= policyRepo.findById(id);

        if(policy!=null){
            policyRepo.delete(policy);
            response.put("message", "policy deleted successfully");
            response.put("status",true);
        }else{
            response.put("message", "Username not found/ Invalid username");
            response.put("status",false);
        }
        return response;
    }

    @Override
    public Map<String, Object> updatePolicy(PolicyDTO policyDTO) {
        Map<String, Object> response=new HashMap<>();
        Policy policy= policyRepo.findByTitle(policyDTO.getTitle());
        if(policy!=null){
            policy.setTitle(policyDTO.getTitle());
            policy.setDescription(policyDTO.getDescription());
            policyRepo.save(policy);
            response.put("message", "policy updated successfully");
            response.put("status",true);
        }else {
            response.put("message", "Username not found/ Invalid username");
            response.put("status",false);
        }
        return response;
    }

    @Override
    public Policy getPolicyById(Long policyId) {
       Optional<Policy> policy= policyRepo.findById(policyId);
       return policy.get();
    }


}
