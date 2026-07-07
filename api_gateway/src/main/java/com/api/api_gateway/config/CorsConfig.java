package com.api.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 🎯 Spécifie explicitement l'URL de ton application React
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));

        // Autorise toutes les méthodes (GET, POST, PUT, DELETE, OPTIONS)
        config.addAllowedMethod("*");

        // Autorise tous les headers, y compris ton Authorization injecté par l'interceptor
        config.addAllowedHeader("*");

        // Nécessaire si tu stockes ou manipules des sessions/cookies partagés
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
} 