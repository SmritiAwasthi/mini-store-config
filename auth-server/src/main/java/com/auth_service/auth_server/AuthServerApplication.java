package com.auth_service.auth_server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}

/*
4. Use Token in API Requests

After login via Auth Service:

POST /auth/login
Content-Type: application/json

{
  "username": "user1",
  "password": "password"
}


Response:

{
  "token": "eyJhbGciOiJIUzI1..."
}


Use this token in the Authorization header when calling any service:

GET /product-service/products
Authorization: Bearer eyJhbGciOiJIUzI1...

* */
