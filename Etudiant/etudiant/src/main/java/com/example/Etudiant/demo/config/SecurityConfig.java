package com.example.Etudiant.demo.config;


import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {


    private final JwtFilter jwtFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {



        return http

                // désactiver csrf pour API REST
                .csrf(csrf -> csrf.disable())


                // JWT = pas de session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )



                .authorizeHttpRequests(auth -> auth



                        // =========================
                        // PUBLIC
                        // =========================


                        .requestMatchers(
                                "/etudiants/registration"
                        )
                        .permitAll()



                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        )
                        .permitAll()



                        // =========================
                        // ETUDIANT
                        // =========================


                        .requestMatchers(
                                "/etudiants/me"
                        )
                        .hasRole("ETUDIANT")



                        // =========================
                        // ADMIN
                        // =========================


                        .requestMatchers(
                                "/etudiants",
                                "/etudiants/{id}",
                                "/etudiants/{id}/valider",
                                "/etudiants/{id}/refuser"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )



                        // =========================
                        // SUPER ADMIN uniquement
                        // =========================


                        .requestMatchers(
                                "/etudiants/{id}"
                        )
                        .hasRole(
                                "SUPER_ADMIN"
                        )



                        // toutes les autres routes
                        .anyRequest()
                        .authenticated()


                )



                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )


                .build();

    }

}