package com.idealista.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ad Ranking Challenge API")
                        .version("1.1")
                        .description("API for Idealista Backend Code Challenge.")
                        .contact(new Contact()
                                .name("Luis Muñoz Martínez")
                                .email("martinezelx@gmail.com")
                                .url("https://github.com/martinezelx"))
                );
    }
}
