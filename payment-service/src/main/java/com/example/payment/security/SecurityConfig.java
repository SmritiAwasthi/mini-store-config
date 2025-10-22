package com.example.payment.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var jwtFilter = new JwtAuthenticationFilter(tokenProvider);

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/actuator/**","/health","/info").permitAll()
                    //.requestMatchers("/api/v1/payments").permitAll()//temporarily
                    .requestMatchers("/api/v1/payments").authenticated()
                    .anyRequest().permitAll()
            )
            .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
