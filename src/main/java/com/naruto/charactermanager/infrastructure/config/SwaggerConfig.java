package com.naruto.charactermanager.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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

## Autenticación

Para usar los endpoints protegidos, primero debes:
1. Hacer login en `/api/auth/login` para obtener un token JWT
2. Hacer clic en el botón "Authorize" (🔒) en la parte superior de esta página
3. Ingresar el token en el formato: `Bearer <tu-token-aqui>`
4. Hacer clic en "Authorize" y luego en "Close"

El token se incluirá automáticamente en todas las peticiones a endpoints protegidos.
                """);

        // Configurar esquema de seguridad JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Ingresa el token JWT obtenido del endpoint de login. Formato: Bearer <token>");

        // Agregar el esquema de seguridad
        io.swagger.v3.oas.models.Components components = new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("bearer-jwt", securityScheme);

        // Agregar requerimiento de seguridad global (opcional, se puede sobrescribir en endpoints específicos)
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearer-jwt");

        return new OpenAPI()
                .servers(List.of(localServer))
                .info(info)
                .components(components)
                .addSecurityItem(securityRequirement);
    }
}

