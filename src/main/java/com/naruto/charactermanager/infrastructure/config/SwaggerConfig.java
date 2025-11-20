package com.naruto.charactermanager.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de Swagger/OpenAPI 3 para documentación de la API
 * Accesible en: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        
        Server localServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Servidor de Desarrollo Local");
        
        Info info = new Info()
                .title("Naruto Character Manager API")
                .version("1.0.0")
                .description("""
# API REST para Sistema de Gestión de Personajes de Naruto

Esta API permite gestionar personajes del universo Naruto y comentarios sobre ellos.
Incluye operaciones CRUD completas para personajes y comentarios.
                """);

        return new OpenAPI()
                .servers(List.of(localServer))
                .info(info);
    }
}

