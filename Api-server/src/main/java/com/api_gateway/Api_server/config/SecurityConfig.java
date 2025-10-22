package com.api_gateway.Api_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final String SECRET = "myverylongsecretkeythatisatleast32chars";


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)  // Disable CSRF for stateless API
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**", "/login", "/user/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/**").permitAll()// Allow unauthenticated access to auth endpoints if needed
                        .anyExchange().authenticated()             // Require authentication for all other endpoints
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder())));// Enable JWT resource server support

        return http.build();
    }
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        byte[] secretBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }
}
/*
* Flow Summary: What happens on each request?

A request hits the API Gateway (e.g., /products/create)

SecurityWebFilterChain checks:

Is the path public (/auth/**, /login, etc.)? ➝ Allow without token

Is the path protected? ➝ Yes ➝ Expect a JWT token in the Authorization header

If token is present:

Decode and validate it using jwtDecoder()

If valid ➝ allow request to proceed

If invalid or expired ➝ return 401 Unauthorized
* */