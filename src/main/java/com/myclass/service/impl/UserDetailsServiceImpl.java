package com.myclass.service.impl;

import com.myclass.entity.User;
import com.myclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Email" +  email + " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getEmail(),
                getGrantedAuthority(user));
    }
    private Collection<GrantedAuthority> getGrantedAuthority(User user){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(user.getRole().getName().equalsIgnoreCase("admin")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROlE_USER"));
        return authorities;
    }
}
