package com.auth_service.auth_server.service;

import com.auth_service.auth_server.entity.User;
import com.auth_service.auth_server.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsService
{
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username));

        // Convert role string (e.g., "ROLE_USER") to GrantedAuthority
       /* This wraps your custom User object in a Spring Security UserDetails object.
       *  Spring Security doesn't know about your custom User entity,
       * so you convert it into a standard format (UserDetails) that Spring understands.
        */
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}