package com.myclass.config;

import io.jsonwebtoken.Claims;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtTokenFilter extends OncePerRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    private JwtTokenProvider tokenProvider;
    public JwtTokenFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("JwtTokenFilter:doFilterInternal ");
        String token= request.getHeader("Authorization");
        if(token!=null){
            try {
                Claims claims= tokenProvider.getClaimsFromToken(token);
                if(!claims.getExpiration().before(new Date())){
                    Authentication authentication = tokenProvider.getAuthentication(claims.getSubject());
                    if(authentication.isAuthenticated()){
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }catch (RuntimeException e){
                try {
                    SecurityContextHolder.clearContext();
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().println(
                            new JSONObject().put("Exception", "Expired or invalid JWT token" +e.getMessage()));
                }catch (IOException | JSONException e1){
                    e1.getMessage();
                }
                return;
            }
        }else {
            logger.info("First time to creating token using UserResourseImpl - authenticate method");
        }
        filterChain.doFilter(request, response);

    }
}
