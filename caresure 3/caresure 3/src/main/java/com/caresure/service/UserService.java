package com.caresure.service;

import com.caresure.dto.LoginDTO;
import com.caresure.dto.NewUserDTO;
import com.caresure.dto.PolicyDTO;
import com.caresure.dto.RenewPolicyDTO;
import com.caresure.pojo.Users;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> createNewUser(NewUserDTO userDTO);

    Map<String,Object> userLogin(LoginDTO loginDTO);

    List<Users> getAllUsers();

    Map<String,Object> deleteByUsername(String username);

    Map<String, Object> updateUser(NewUserDTO userDTO);

    Map<String, Object> addPolicyToUser(String username, Long policyId);

    Map<String, Object> adminLogin(LoginDTO loginDTO);

    Map<String, Object> renewUserPolicy(RenewPolicyDTO renewPolicyDTO);

    Users getUserDetailsByUsername(String username);

    Map<String, Object> createNewAdmin(NewUserDTO userDTO);
}
