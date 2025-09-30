package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for APIs
            .csrf(csrf -> csrf.disable())

            // Authorize requests
            .authorizeHttpRequests(auth -> auth
                // Allow Swagger and API docs
                .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).authenticated()

                // Allow Blog endpoints without authentication (for testing)
                .requestMatchers(HttpMethod.GET,"/blogs/**").permitAll()

                // All other requests require authentication
                .anyRequest().authenticated()
            )

            // OAuth2 Login (if needed for other endpoints)
            .oauth2Login()
            
            // Enable JWT Resource Server
            .and()
            .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}
