package com.api.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.disable())
                .authorizeExchange(exchanges -> exchanges

                        // public endpoints
                        .pathMatchers("/auth/**").permitAll()

                        // roles
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/etudiant/**").hasRole("ETUDIANT")
                        .pathMatchers("/api/enseignant/**").hasRole("ENSEIGNANT")

                        .pathMatchers("/api/actualites/**").permitAll()
                        .pathMatchers("/api/encadreurs/**").permitAll()
                        .pathMatchers("/api/ressours/**").hasAnyRole("ETUDIANT", "ENSEIGNANT", "ADMIN")
                        .pathMatchers("/api/cours/**").hasAnyRole("ETUDIANT", "ENSEIGNANT", "ADMIN")
                        .pathMatchers("/api/chapitres/**").hasAnyRole("ETUDIANT", "ENSEIGNANT", "ADMIN")
                        .pathMatchers("/api/domains/**").hasAnyRole("ETUDIANT", "ENSEIGNANT", "ADMIN")
                        .pathMatchers("/api/emails/**").permitAll()
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
                        .pathMatchers("/api/users/**").hasRole("ADMIN")

                        // others
                        .anyExchange().authenticated()
                )
                .build();
    }

}