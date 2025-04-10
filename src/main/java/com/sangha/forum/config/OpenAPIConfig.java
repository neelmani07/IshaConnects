package com.sangha.forum.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server()
                .url("http://localhost:8080")
                .description("Development server");

        Contact contact = new Contact()
                .name("Sangha Connect Team")
                .email("support@sanghaconnect.com")
                .url("https://www.sanghaconnect.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Sangha Connect Forum API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints for managing the Sangha Connect Forum.")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
} 