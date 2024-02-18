package com.caresure.service;

import com.caresure.dto.LoginDTO;

import java.util.Map;

public interface AdminService {
    Map<String, String> login(LoginDTO loginDTO);
}
