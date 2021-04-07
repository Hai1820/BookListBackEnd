package com.myclass.config;

import com.myclass.entity.Role;
import com.myclass.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider implements Serializable {
    @Value("${jwt.secret-ket}")
    private String secretKey;

    public JwtTokenProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    private long validityInMilliseconds=86400000;
    public String createToken(String username, Role role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", role);
        Date now = new Date();
        return Jwts.builder().setClaims(claims).setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }
    private final UserDetailsServiceImpl userDetailsService;
    public Authentication getAuthentication(String email){
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return  new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),
                userDetails.getAuthorities());
    }
    public Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
