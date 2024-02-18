package com.caresure.jwt;

import com.caresure.pojo.Users;
import com.caresure.repository.UserRepo;
import com.sun.jdi.InternalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";

    public static final long JWT_TOKEN_VALIDITY=60000*60L;
    public static final String UNAUTHORIZED="Unauthorized access";
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private UserRepo userRepo;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String username=userDetails.getUsername();
        Users user= this.userRepo.findByUsername(username);
        claims.put("role",user.getRole());
        return createToken(claims,username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public UserDetails validateTokenAndGetUserDetails(HttpServletRequest request) {
        String requestHeaderToken=request.getHeader("Authorization");
        String username=null;
        String jwtToken=null;
        Users user= null;
        UserDetails userDetails=null;

            if( requestHeaderToken.startsWith("Bearer ")){
                try{
                    jwtToken=requestHeaderToken.substring(7);
                    username=this.extractUsername(jwtToken);
                    user=userRepo.findByToken(jwtToken);
                    userDetails=customUserDetailService.loadUserByUsername(username);
                 }catch (ExpiredJwtException ex){
                    throw new BadCredentialsException(UNAUTHORIZED);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    throw new InternalException("Something went wrong!!"+ex);
                }
                if(username!=null && user!=null && userDetails!=null
                        && validateToken(jwtToken,userDetails)
                        && SecurityContextHolder.getContext().getAuthentication()==null) {
                    return userDetails;
                    }else
                        throw new BadCredentialsException(UNAUTHORIZED);
                }else
                    throw new BadCredentialsException(UNAUTHORIZED);
    }
}