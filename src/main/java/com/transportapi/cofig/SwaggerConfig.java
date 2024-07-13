package com.transportapi.cofig;

import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Transport API",
        version = "1.0.0",
        description = "Api basica para tranporte de personas",
        contact = @Contact(
            name = "Luis Gonzalez Sanchez",
            url = "http://localhost:8080",
            email = "luis.gonzalez.sanchez.404@gmail.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html")),

    servers = {
        @Server(
            description = "Dev Server",
            url = "http://localhost:8080"
        )
    },
    security = @SecurityRequirement(name = "Token Authentication"))

    @SecurityScheme(
        name = "Token Authentication",
        description = "Token access for requests Api",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        paramName = HttpHeaders.AUTHORIZATION,
        bearerFormat = "JWT"
    )

public class SwaggerConfig {}
