package com.rayitosdesol.solarapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SolarApp API")
                        .version("1.0")
                        .description("API documentation for SolarApp")
                        .contact(new Contact()
                                .name("Rayitos de Sol")
                                .url("https://www.rayitosdesol.com")
                                .email("info@rayitosdesol.com")));
    }
}