package com.caresure.controller;

import com.caresure.dto.PolicyDTO;
import com.caresure.pojo.Policy;
import com.caresure.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/policy")
@PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
public class PolicyController {
    @Autowired
    private PolicyService policyService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String create(@RequestBody PolicyDTO policyDTO){
        return policyService.addNewPolicy(policyDTO);
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public List<Policy> getAllPolicies(){
        return policyService.getAllPolicies();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Map<String,Object> deletePolicyByTitle(@RequestParam(name = "id") int id){
        return policyService.deletePolicyById(id);
    }
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Map<String,Object> updatePolicy(@RequestBody PolicyDTO policyDTO){
        return policyService.updatePolicy(policyDTO);
    }

    @GetMapping("/getById")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public Policy getPolicyById(@RequestParam(name = "policyid") Long policyId){
        return policyService.getPolicyById(policyId);
    }


}
