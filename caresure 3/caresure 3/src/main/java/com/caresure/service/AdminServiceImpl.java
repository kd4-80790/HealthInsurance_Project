package com.caresure.service;

import com.caresure.dto.LoginDTO;
import com.caresure.pojo.Admin;
import com.caresure.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepo adminRepo;
    @Override
    public Map<String, String> login(LoginDTO loginDTO) {
        Map<String, String> response=new HashMap<>();
        Admin admin=adminRepo.findByUsername(loginDTO.getUsername());
        if(admin!=null && admin.getPassword().equals(loginDTO.getPassword())){
            response.put("message", "user login successfully");
            response.put("token","adxascascsac");
        }else{
            response.put("message", "Username not found/ Invalid username");
        }
        return response;
    }
}
