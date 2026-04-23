package com.realestate.config;

import com.realestate.security.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity   // ✅ REQUIRED FOR @PreAuthorize
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // ✅ ENABLE CORS
            .cors(cors -> {})

            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // 🔓 PUBLIC
                .requestMatchers(
                        "/",
                        "/api/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // 👤 CUSTOMER + ADMIN
                .requestMatchers("/api/properties/**")
                .hasAnyRole("CUSTOMER", "ADMIN")

                // 🔐 ADMIN ONLY
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                // fallback
                .anyRequest().authenticated()
            )

            .addFilterBefore(jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

   
}
