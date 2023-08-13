package com.engeto.ProjektRegistracnisystem.settings;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**") // Nahraď tohle za cestu tvého API
                .allowedOrigins("*") // Povol všechny zdroje (pro vývoj můžeš nastavit konkrétní doménu)
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Povolené HTTP metody
    }
}
