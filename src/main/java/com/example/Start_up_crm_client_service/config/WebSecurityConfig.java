package com.example.Start_up_crm_client_service.config;

import com.example.Start_up_crm_client_service.filter.JwtRequestFilter;
import com.example.Start_up_crm_client_service.service.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req

                        // ✅ Allow preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ Public APIs
                        .requestMatchers(
                                "/api/client/**",
                                "/api/client/login",
                                "/api/client/signup",
                                "/api/hr/login",
                                "/api/hr/signup"
                        ).permitAll()

                        // ================= 🔥 EMPLOYEE Profile  APIs =================

                        // Create employee (public)
                        .requestMatchers(HttpMethod.POST, "/api/employees/profile").authenticated()

                        // All employee endpoints (secured)
                        .requestMatchers("/api/employees/**").authenticated()

                        // 🔥 NEW PROFILE ENDPOINTS
                        .requestMatchers("/api/employees/profile/**").authenticated()

                        // ===================================================

                        // Feign internal
                        .requestMatchers("/internal/**").permitAll()

                        // Existing public endpoints
                        .requestMatchers(
                                "/auth/**",
                                "/api/v1/users/request-password-reset",
                                "/api/v1/users/reset-password"
                        ).permitAll()

                        // ================= 🔥 PROFILE APIs =================
                        .requestMatchers("/api/profile/**").authenticated()

                        // Permissions
                        .requestMatchers("/api/v1/permissions/**")
                        .hasAnyAuthority("ROLE_USER","ROLE_EMP","ROLE_ADMIN","ROLE_ORG")

                        // Users
                        .requestMatchers("/api/v1/users/**")
                        .hasAnyRole("USER", "ADMIN", "INSTRUCTOR", "ORG","EMP")

                        // Profile
                        .requestMatchers("/api/v1/profile/**")
                        .hasAnyRole("USER","ORG", "ADMIN")

                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .userDetailsService(customUserDetailsService)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler(customAccessDeniedHandler))
                .build();
    }

    // ================= CORS =================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
        );
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // ================= BEANS =================

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}