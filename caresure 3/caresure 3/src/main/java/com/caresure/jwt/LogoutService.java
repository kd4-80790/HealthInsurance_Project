package com.caresure.jwt;

import com.caresure.pojo.Users;
import com.caresure.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    Logger logger = LoggerFactory.getLogger(LogoutService.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetails userDetails=jwtUtil.validateTokenAndGetUserDetails(request);
        Users user = null;
        String jwtToken=null;

        if(userDetails!=null){
            String requestHeaderToken=request.getHeader("Authorization");
            jwtToken = requestHeaderToken.substring(7);
            user=userRepo.findByToken(jwtToken);
            if(user!=null){
                user.setToken(null);
                userRepo.save(user);
                logger.info("User logout successfully. Username:{}",jwtUtil.extractUsername(jwtToken));
            }
        }else
            throw new BadCredentialsException("User already logout");
    }
}
