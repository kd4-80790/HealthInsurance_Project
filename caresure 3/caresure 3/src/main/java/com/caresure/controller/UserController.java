package com.caresure.controller;

import com.caresure.dto.NewUserDTO;
import com.caresure.dto.PolicyDTO;
import com.caresure.dto.RenewPolicyDTO;
import com.caresure.pojo.Users;
import com.caresure.repository.TransactionRepo;
import com.caresure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Users> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public Users getUserDetailsByUsername(@RequestParam(name = "username") String username){
        return userService.getUserDetailsByUsername(username);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Map<String,Object> deleteUserByUsername(@RequestParam(name = "username") String username){
       return userService.deleteByUsername(username);
    }
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public Map<String, Object> updateUser(@RequestBody NewUserDTO userDTO){
        return userService.updateUser(userDTO);
    }

    @PostMapping("/add/policy")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public Map<String, Object> addPolicyToUser(@RequestParam(name = "username") String username, @RequestParam(name = "policyid") Long policyId){
        return userService.addPolicyToUser(username,policyId);
    }
    @PostMapping("/renew/policy")
    @PreAuthorize("hasAnyRole('NORMAL')")
    public Map<String,Object> renewUserPolicy(@RequestBody RenewPolicyDTO renewPolicyDTO){
        return userService.renewUserPolicy(renewPolicyDTO);
    }

}
