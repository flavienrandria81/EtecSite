package com.api.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {})
                .authorizeExchange(exchanges -> exchanges

                        // public endpoints 
                        .pathMatchers("/auth/**").permitAll()

                        // roles
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/etudiant/**").hasRole("ETUDIANT")
                        .pathMatchers("/api/enseignant/**").hasRole("ENSEIGNANT")

                        .pathMatchers("/api/actualites/**").permitAll()
                        .pathMatchers("/api/encadreurs/**").permitAll()
                        .pathMatchers("/api/ressours/**").hasRole("ETUDIANT")
                        .pathMatchers("/api/cours/**").hasRole("ETUDIANT")
                        .pathMatchers("/api/chapitres/**").hasRole("ETUDIANT")
                        .pathMatchers("/api/domains/**").hasRole("ETUDIANT")
                        .pathMatchers("api/emails/**").permitAll()
                        .pathMatchers("/api/emploiDuTemps/**").permitAll()
                        .pathMatchers("/api/encadrements/**").permitAll()
                        .pathMatchers("/api/filiers/**").permitAll()
                        .pathMatchers("/api/historiques/**").permitAll()
                        .pathMatchers("/api/matieres/**").permitAll()
                        .pathMatchers("/api/memoires/**").permitAll()
                        .pathMatchers("/api/moyennes/**").permitAll()
                        .pathMatchers("/api/niveau/**").permitAll()
                        .pathMatchers("/api/notes/**").permitAll()
                        .pathMatchers("/api/notifications/**").permitAll()
                        .pathMatchers("/api/organigrammes/**").permitAll()
                        .pathMatchers("/api/presences/**").permitAll()
                        .pathMatchers("/api/mots/**").permitAll()
                        .pathMatchers("/api/semestres/**").permitAll()
                        .pathMatchers("/api/slides/**").permitAll()
                        .pathMatchers("/api/anneesUniv/**").permitAll()

                        // others
                        .anyExchange().authenticated()
                )
                .build();
    }

}
