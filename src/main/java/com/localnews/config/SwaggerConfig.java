package com.localnews.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(getInfo())
                .servers(List.of(
                        new Server().url("https://localhost:8081").description("Development Environment")
                ));
    }

    private Info getInfo() {
        return new Info()
                .title("News App Rest API")
                .description("Api documentation")
                .version("v1.0");
    }

}
