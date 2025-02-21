package com.rayitosdesol.solarapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");  // Origen permitido
        config.addAllowedMethod("*");  // Permitir todos los métodos (GET, POST, etc.)
        config.addAllowedHeader("*");  // Permitir todos los encabezados
        config.setAllowCredentials(true);  // Permitir credenciales (opcional)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Aplicar a todas las rutas

        return source;
    }
}
