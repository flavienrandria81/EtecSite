package com.api.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {

        return builder.routes()

                // =========================
                // UTILISATEUR SERVICE
                // =========================
                .route("utilisateur", r -> r.path("/api/users/**")
                        .uri("lb://UTILISATEUR"))

                // =========================
                // AUTH
                // =========================
                .route("auth", r -> r.path("/api/auth/**")
                        .uri("lb://UTILISATEUR"))

                // =========================
                // ADMIN SERVICE
                // =========================
                .route("admin", r -> r.path("/api/admin/**")
                        .uri("lb://ADMIN"))

                // =========================
                // ORGANIGRAMMES
                // =========================
                .route("organigrammes", r -> r.path("/api/organigrammes/**")
                        .uri("lb://ADMIN"))

                // =========================
                // NOTIFICATIONS
                // =========================
                .route("notifications", r -> r.path("/api/notifications/**")
                        .uri("lb://ADMIN"))

                // =========================
                // ANNÉES UNIVERSITAIRES
                // =========================
                .route("anneesUniv", r -> r.path("/api/anneesUniv/**")
                        .uri("lb://ADMIN"))

                // =========================
                // MOTS
                // =========================
                .route("mots", r -> r.path("/api/mots/**")
                        .uri("lb://ADMIN"))

                // =========================
                // SLIDES
                // =========================
                .route("slides", r -> r.path("/api/slides/**")
                        .uri("lb://slides"))

                // =========================
                // ENCADREMENTS
                // =========================
                .route("encadrements", r -> r.path("/api/encadrements/**")
                        .uri("lb://ADMIN"))

                // =========================
                // MEMOIRES
                // =========================
                .route("memoires", r -> r.path("/api/memoires/**")
                        .uri("lb://ADMIN"))

                // =========================
                // ETUDIANT SERVICE
                // =========================
                .route("etudiants", r -> r.path(
                                "/api/etudiant",
                                "/api/etudiant/**",
                                "/api/etudiants",
                                "/api/etudiants/**")
                        .filters(f -> f.rewritePath(
                                "/api/etudiant[s]?(?<segment>/?.*)",
                                "/etudiants${segment}"
                        ))
                        .uri("lb://ETUDIANT"))

                .route("notes", r -> r.path("/api/notes/**")
                        .uri("lb://ETUDIANT"))

                .route("moyennes", r -> r.path("/api/moyennes/**")
                        .uri("lb://ETUDIANT"))

                .route("presences", r -> r.path("/api/presences/**")
                        .uri("lb://ETUDIANT"))

                .route("historiques", r -> r.path("/api/historiques/**")
                        .uri("lb://ETUDIANT"))

                // =========================
                // ENSEIGNANT SERVICE
                // =========================
                .route("enseignants", r -> r.path(
                                "/api/enseignant",
                                "/api/enseignant/**",
                                "/api/enseignants",
                                "/api/enseignants/**",
                                "/api/Enseignants",
                                "/api/Enseignants/**")
                        .filters(f -> f.rewritePath(
                                "/api/enseignant[s]?(?<segment>/?.*)",
                                "/api/Enseignants${segment}"
                        ))
                        .uri("lb://ENSEIGNANT"))

                // =========================
                // COURS SERVICE
                // =========================
                .route("cours", r -> r.path("/api/cours/**")
                        .uri("lb://COURS"))

                .route("matieres", r -> r.path("/api/matieres/**")
                        .uri("lb://COURS"))

                .route("chapitres", r -> r.path("/api/chapitres/**")
                        .uri("lb://COURS"))

                .route("domains", r -> r.path("/api/domains/**")
                        .uri("lb://COURS"))

                .route("ressources", r -> r.path("/api/ressours/**")
                        .uri("lb://COURS"))

                .route("semestres", r -> r.path("/api/semestres/**")
                        .uri("lb://COURS"))

                .route("niveau", r -> r.path("/api/niveau/**")
                        .uri("lb://COURS"))

                .route("filiers", r -> r.path(
                                "/api/filiers",
                                "/api/filiers/**",
                                "/api/filieres",
                                "/api/filieres/**")
                        .uri("lb://FILIERES"))

                .route("emploiDuTemps", r -> r.path("/api/emploiDuTemps/**")
                        .uri("lb://COURS"))

                .route("formationInitiale", r -> r.path("/api/formationInitiale/**")
                        .uri("lb://COURS"))

                .route("formationContinue", r -> r.path("/api/formationContinue/**")
                        .uri("lb://COURS"))

                .route("formationEnLigne", r -> r.path(
                                "/api/formationEnLigne",
                                "/api/formationEnLigne/**")
                        .filters(f -> f.rewritePath(
                                "/api/formationEnLigne(?<segment>/?.*)",
                                "/formation_enligne${segment}"
                        ))
                        .uri("http://localhost:8083"))

                // =========================
                // ACTUALITES
                // =========================
                .route("actualites", r -> r.path("/api/actualites/**")
                        .uri("lb://ACTUALITE"))

                // =========================
                // ENCADREURS
                // =========================
                .route("encadreurs", r -> r.path("/api/encadreurs/**")
                        .uri("lb://ENCADREURS"))

                // =========================
                // CONTACTS
                // =========================
                .route("contacts", r -> r.path("/api/contacts/**")
                        .uri("lb://CONTACTS"))

                // =========================
                // EMAILS
                // =========================
                .route("emails", r -> r.path("/api/emails/**")
                        .uri("lb://ADMIN"))

                // =========================
                // VISIO
                // =========================
                .route("visio", r -> r.path("/api/visio/**")
                        .uri("lb://VISIO"))

                .route("visioPages", r -> r.path(
                                "/visio",
                                "/visio/**")
                        .uri("lb://VISIO"))

                .route("visioWs", r -> r.path("/visio-ws/**")
                        .uri("lb:ws://VISIO"))

                .build();
    }
}