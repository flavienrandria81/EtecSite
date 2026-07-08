package com.api.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder,
                               JwtAuthenticationFilter filter) {

        return builder.routes()

                // =========================
                // UTILISATEUR SERVICE 
                // =========================
                .route("utilisateur", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://UTILISATEUR"))

                // =========================
                // COURS SERVICE
                // =========================
                .route("cours", r -> r.path("/api/cours/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://COURS"))

                // =========================
                // ADMIN SERVICE
                // =========================
                .route("admin", r -> r.path("/api/admin/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://ADMIN"))

                // =========================
                // AUTH SERVICE (LOGIN PUBLIC)
                // =========================
                .route("auth", r -> r.path("/api/auth/**")
                        .uri("lb://UTILISATEUR")) // ou AUTH-SERVICE si tu as un service séparé

                // =========================
                // ETUDIANT SERVICE (optionnel si séparé)
                // =========================
                .route("etudiant", r -> r.path("/api/etudiant/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://ETUDIANT"))

                // =========================
                // ENSEIGNANT SERVICE (optionnel si séparé)
                // =========================
                .route("enseignant", r -> r.path("/api/enseignant/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://ENSEIGNANT"))

                // =========================
                // ACTUALITES SERVICE (PUBLIC)
                // =========================
                .route("actualites", r -> r.path("/api/actualites/**")
                        .uri("lb://ACTUALITE")) // Redirige vers le microservice enregistré sous le nom ACTUALITE sur Eureka


                .build();
    }
}
