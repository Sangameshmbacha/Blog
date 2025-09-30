package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // Swagger docs require authentication (change to permitAll() if you want them public)
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).authenticated()

                // Allow only GET requests to /blogs/** without authentication
                .requestMatchers(HttpMethod.GET, "/blogs/**").permitAll()

                // All other requests require authentication
                .anyRequest().authenticated()
            )

            // Enable OAuth2 login (e.g., Google login)
            .oauth2Login()

            // Enable JWT support
            .and()
            .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}
