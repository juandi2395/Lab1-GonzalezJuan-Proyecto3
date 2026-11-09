
package com.gonzalezjuan.gonzalezjuanlab1proyecto3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        // Permitir el frontend Angular 22
        configuration.setAllowedOrigins(
                List.of("http://localhost:4200")
        );

        // Metodos HTTP permitidos
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        // Encabezados permitidos
        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"
                )
        );

        // No utilizamos cookies para autenticacion
        configuration.setAllowCredentials(false);

        // Cache de solicitudes preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // Aplicar CORS a los endpoints de la API
        source.registerCorsConfiguration(
                "/api/**",
                configuration
        );

        return source;
    }
}
