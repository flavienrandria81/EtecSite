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

                // =========================
                // coursenligne
                // =========================
                .route("cours en ligne", r -> r.path("/api/coursenligne/**")
                        .uri("lb://Formations En Ligne"))

                // =========================
                // empoiDuTemps
                // =========================
                .route("emploidutemps", r -> r.path("/api/emploidutemps/**")
                        .uri("lb://Emploie du temps"))

                // =========================
                // Encadrement
                // =========================
                .route("Encadrement", r -> r.path("/api/Encadrement/**")
                        .uri("lb://Encadrement"))

                // =========================
                // encadreur
                // =========================
                .route("Encadreur", r -> r.path("/api/Encadreur/**")
                        .uri("lb://Encadreur"))

                // =========================
                // filiere
                // =========================
                .route("filiere", r -> r.path("/api/filiere/**")
                        .uri("lb://filiere"))

                // =========================
                // historique
                // =========================
                .route("historique", r -> r.path("/api/historique/**")
                        .uri("lb://Historiques"))

                // =========================
                // matiere
                // =========================
                .route("matiere", r -> r.path("/api/matiere/**")
                        .uri("lb://matiere"))

                // =========================
                // memoire
                // =========================
                .route("memoire", r -> r.path("/api/memoire/**")
                        .uri("lb://Memoires"))

                // =========================
                // messagerie
                // =========================
                .route("messagerie", r -> r.path("/api/messagerie/**")
                        .uri("lb://Message"))

                // =========================
                // moyenne
                // =========================
                .route("moyenne", r -> r.path("/api/moynne/**")
                        .uri("lb://Moyennes"))

                // =========================
                // niveau
                // =========================
                .route("niveau", r -> r.path("/api/niveaux/**")
                        .uri("lb://Niveaux"))

                // =========================
                // note
                // =========================
                .route("note", r -> r.path("/api/note/**")
                        .uri("lb://Notes"))

                // =========================
                // notifications
                // =========================
                .route("notification", r -> r.path("/api/notification/**")
                        .uri("lb://Notifications"))

                // =========================
                // organigramme
                // =========================
                .route("organigramme", r -> r.path("/api/organigramme/**")
                        .uri("lb://Organigrammes"))

                // =========================
                // presence
                // =========================
                .route("presence", r -> r.path("/api/presence/**")
                        .uri("lb://Presence"))

                // =========================
                // President
                // =========================
                .route("president", r -> r.path("/api/president/**")
                        .uri("lb://Presidents"))


                // =========================
                // Profiles
                // =========================
                .route("profile", r -> r.path("/api/profile/**")
                        .uri("lb://Parametres"))


                // =========================
                // Progression
                // =========================
                .route("progression", r -> r.path("/api/progression/**")
                        .uri("lb://Progressions"))

                // =========================
                // quiz
                // =========================
                .route("quiz", r -> r.path("/api/quiz/**")
                        .uri("lb://Quiz"))

                // =========================
                // semestre
                // =========================
                .route("semestre", r -> r.path("/api/semestre/**")
                        .uri("lb://Semestre"))

                // =========================
                // slides
                // =========================
                .route("slide", r -> r.path("/api/slide/**")
                        .uri("lb://Slide"))

                // =========================
                // univairsitaire
                // =========================
                .route("univesitaire", r -> r.path("/api/univesitaire/**")
                        .uri("lb://Universitaire"))

                // =========================
                // utilisateur
                // =========================
                .route("organigramme", r -> r.path("/api/organigramme/**")
                        .uri("lb://Organigrammes"))

                // =========================
                // visio
                // =========================
                .route("visio", r -> r.path("/api/visio/**")
                        .uri("lb://Visio"))

                // =========================
                // devoir
                // =========================
                .route("devoir", r -> r.path("/api/devoir/**")
                        .uri("lb://Devoire"))

                .build();
    }
}
