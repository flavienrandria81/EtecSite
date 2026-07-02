package com.api.actualite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // On cible exactement le même dossier absolu
        String uploadFolderPath = System.getProperty("user.dir") + File.separator + "uploads";

        // Tout appel sur /uploads/** ira piocher directement dans ce dossier physique
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadFolderPath + File.separator);
    }
}