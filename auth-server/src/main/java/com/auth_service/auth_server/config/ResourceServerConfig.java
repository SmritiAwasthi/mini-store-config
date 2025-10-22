package com.auth_service.auth_server.config;

import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("Applying custom security filter chain");
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/auth/login","/user/**").permitAll() // allow login endpoint
                        .anyRequest().authenticated()
                );
              //  .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("*"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            cors.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", cors);
            return source;
        }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationConfiguration authenticationConfiguration() {
        return new AuthenticationConfiguration();
    }

}
