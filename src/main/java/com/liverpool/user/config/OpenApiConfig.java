package com.liverpool.user.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración global de OpenAPI/Swagger para los endpoints de Usuarios.
 * Incluye esquema de autenticación Bearer JWT y metadatos de la API.
 *
 * @author arturhc
 */
@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {

    return new OpenAPI()
        // Requerir el esquema de seguridad 'BearerAuth' en todos los endpoints
        .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
        // Definir el esquema de seguridad HTTP Bearer JWT
        .components(new Components().addSecuritySchemes("BearerAuth",
            new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        ))
        // Información general de la API
        .info(new Info()
            .title("User Management API")
            .version("1.0")
            .description("API para la gestión de usuarios y autenticación JWT")
            .contact(new Contact()
                .name("Arturo Cordero")
                .email("arturh.sw@gmail.com")
                .url("https://easycommerce.com")));
  }

}
