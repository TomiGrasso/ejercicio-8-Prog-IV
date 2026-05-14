package com.unidad5.ejercicio8.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public final class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/libros").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/libros").hasAnyRole("BIBLIOTECARIO","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/libros/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/prestamos").hasRole("LECTOR")
                        .requestMatchers(HttpMethod.GET,"/api/prestamos/mis-prestamos").hasRole("LECTOR")
                        .requestMatchers(HttpMethod.GET,"/api/prestamos").hasAnyRole("BIBLIOTECARIO","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/prestamos/{id}/**").hasRole("BIBLIOTECARIO")
                        .anyRequest().authenticated()
                );
                http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                http.exceptionHandling(exception -> exception
                        // ERROR 401
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("""
                                {
                                    "error": "No autenticado"
                                }
                            """);
                        })

                        // ERROR 403
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("""
                                {
                                    "error": "Sin permisos"
                                }
                            """);
                        })
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception{
        return config.getAuthenticationManager();
    }

}
