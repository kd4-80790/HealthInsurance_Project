package com.caresure.controller;

import com.caresure.dto.LoginDTO;
import com.caresure.dto.NewUserDTO;
import com.caresure.jwt.JwtUtil;
import com.caresure.service.AdminService;
import com.caresure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @PostMapping("user/login")
    public Map<String,Object> userLogin(@RequestBody LoginDTO loginDTO){
        return userService.userLogin(loginDTO);
    }
    @PostMapping("admin/login")
    public Map<String,Object> adminLogin(@RequestBody LoginDTO loginDTO){
        return userService.adminLogin(loginDTO);
    }


    @PostMapping("user/create")
    public Map<String, Object> create(@RequestBody NewUserDTO userDTO){
        return userService.createNewUser(userDTO);
    }
    @PostMapping("admin/create")
    public Map<String, Object> createAdmin(@RequestBody NewUserDTO userDTO){
        return userService.createNewAdmin(userDTO);
    }

}
