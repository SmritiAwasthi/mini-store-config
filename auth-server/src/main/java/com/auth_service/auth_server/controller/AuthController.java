package com.auth_service.auth_server.controller;

import com.auth_service.auth_server.entity.AuthRequest;
import com.auth_service.auth_server.entity.AuthResponse;
import com.auth_service.auth_server.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private  AuthenticationManager authenticationManager;
    @Autowired private  JwtUtil jwtUtil;

//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
     //   String encodedPassword = passwordEncoder.encode(request.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(request.getUsername(), "USER");
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
