package com.caresure.service;

import com.caresure.dto.LoginDTO;
import com.caresure.dto.NewUserDTO;
import com.caresure.dto.RenewPolicyDTO;
import com.caresure.jwt.CustomUserDetailService;
import com.caresure.jwt.JwtUtil;
import com.caresure.pojo.Policy;
import com.caresure.pojo.Transactions;
import com.caresure.pojo.Users;
import com.caresure.repository.PolicyRepo;
import com.caresure.repository.TransactionRepo;
import com.caresure.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PolicyRepo policyRepo;
    @Override
    public Map<String, Object> createNewUser(NewUserDTO userDTO) {
        Map<String,Object> response=new HashMap<>();
        Users user=userRepo.findByUsername(userDTO.getUsername());
        if(user==null){
            user=new Users();
            user.setUsername(userDTO.getUsername());
            user.setName(userDTO.getName());
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            user.setAge(userDTO.getAge());
            user.setGender(userDTO.getGender());
            user.setCity(userDTO.getCity());
            user.setAddress(userDTO.getAddress());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setDateOfBirth(user.getDateOfBirth());
            user.setRole("ROLE_NORMAL");
            user.setCountry(userDTO.getCountry());
            user.setCreatedTime(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            user.setModifiedTime(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            userRepo.save(user);
            response.put("message", "user created successfully");
            response.put("status",true);
        }else {
            response.put("message", "Username already present");
            response.put("status",false);
        }
        return response;
    }
    @Override
    public Map<String, Object> createNewAdmin(NewUserDTO userDTO) {
        Map<String,Object> response=new HashMap<>();
        Users user=userRepo.findByUsername(userDTO.getUsername());
        if(user==null){
            user=new Users();
            user.setUsername(userDTO.getUsername());
            user.setName(userDTO.getName());
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            user.setAge(userDTO.getAge());
            user.setGender(userDTO.getGender());
            user.setCity(userDTO.getCity());
            user.setAddress(userDTO.getAddress());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setDateOfBirth(user.getDateOfBirth());
            user.setRole("ROLE_ADMIN");
            user.setCountry(userDTO.getCountry());
            user.setCreatedTime(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            user.setModifiedTime(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            userRepo.save(user);
            response.put("message", "admin created successfully");
            response.put("status",true);
        }else {
            response.put("message", "Username already present");
            response.put("status",false);
        }
        return response;
    }

    @Override
    public Map<String,Object> userLogin(LoginDTO loginDTO) {
        Map<String,Object> response=new HashMap<>();
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(loginDTO.getUsername());
            String token = this.jwtUtil.generateToken(userDetails);
            Users user = userRepo.findByUsername(loginDTO.getUsername());
            user.setToken(token);
            userRepo.save(user);
            response.put("message", "user login successfully");
            response.put("token",token);
            response.put("status","true");
        }catch (BadCredentialsException ex) {
            response.put("message", "Username not found/Invalid username");
            response.put("status","false");
        }
        return response;
    }

    @Override
    public List<Users> getAllUsers() {
         return userRepo.findAll();
    }

    @Override
    public Map<String,Object> deleteByUsername(String username) {
        Map<String, Object> response=new HashMap<>();
        Users user=userRepo.findByUsername(username);
        if(user!=null){
            userRepo.delete(user);
            response.put("message", "user deleted successfully");
            response.put("status",true);
        }else{
            response.put("message", "Username not found/ Invalid username");
            response.put("status",false);
        }
        return response;
    }

    @Override
    public Map<String, Object> updateUser(NewUserDTO userDTO) {
        Map<String, Object> response=new HashMap<>();
        Users user=userRepo.findByUsername(userDTO.getUsername());
        if(user!=null){
            user.setName(userDTO.getName());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setAge(userDTO.getAge());
            user.setGender(userDTO.getGender());
            user.setCity(userDTO.getCity());
            user.setAddress(userDTO.getAddress());
            user.setCountry(userDTO.getCountry());
            user.setModifiedTime(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            userRepo.save(user);
            response.put("message", "user updated successfully");
            response.put("status",true);
        }else {
            response.put("message", "Username not found/ Invalid username");
            response.put("status",false);
        }
        return response;
    }

    @Override
    public Map<String, Object> addPolicyToUser(String username, Long policyId) {
        Map<String, Object> response=new HashMap<>();
        Users user=userRepo.findByUsername(username);
        if(user.getAge()>75){
            response.put("message", "Your age is greater than 75. You are not allowed to buy policy");
            response.put("status", false);
        }else {
            Optional<Policy> policy = policyRepo.findById(policyId);
            user.setPolicyId(policyId);
            Transactions transaction = new Transactions();
            transaction.setUsername(username);
            transaction.setPolicyId(policyId);
            transaction.setStatus("Completed");
            transaction.setAmount(policy.get().getAmount());
            transaction.setTransactionDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            userRepo.save(user);
            transactionRepo.save(transaction);
            response.put("message", "Policy added to the user");
            response.put("user", user);
            response.put("status", true);
        }
        return response;
    }

    @Override
    public Map<String, Object> adminLogin(LoginDTO loginDTO) {
        Map<String,Object> response=new HashMap<>();
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(loginDTO.getUsername());
            String token = this.jwtUtil.generateToken(userDetails);
            Users user = userRepo.findByUsername(loginDTO.getUsername());
            if(user!=null && user.getRole().equals("ROLE_ADMIN")) {
                user.setToken(token);
                userRepo.save(user);
                response.put("message", "user created successfully");
                response.put("token", token);
                response.put("status", true);
            }else {
                response.put("message", "User not an admin");
                response.put("status",false);
            }
        }catch (BadCredentialsException ex) {
            response.put("message", "Username not found/Invalid username");
            response.put("status",false);
        }
        return response;
    }

    @Override
    public Map<String, Object> renewUserPolicy(RenewPolicyDTO renewPolicyDTO) {
        Map<String, Object> response=new HashMap<>();
        Users user=userRepo.findByUsername(renewPolicyDTO.getUsername());
        Optional<Policy> policy = policyRepo.findById(renewPolicyDTO.getPolicyId());
        user.setPolicyId(renewPolicyDTO.getPolicyId());
        Transactions transaction=new Transactions();
        transaction.setUsername(renewPolicyDTO.getUsername());
        transaction.setPolicyId(renewPolicyDTO.getPolicyId());
        transaction.setStatus("Completed");
        transaction.setAmount(policy.get().getAmount());
        transaction.setTransactionDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        userRepo.save(user);
        transactionRepo.save(transaction);
        response.put("message", "Policy renew to the user");
        response.put("user",user);
        response.put("status",true);
        return response;
    }

    @Override
    public Users getUserDetailsByUsername(String username) {
        return userRepo.findByUsername(username);
    }


}
