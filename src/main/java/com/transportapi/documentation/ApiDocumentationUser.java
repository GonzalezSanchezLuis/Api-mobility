package com.transportapi.documentation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public class ApiDocumentationUser {
    public interface Schemas{
     // Define schemas reutilizables para las respuestas y solicitudes de JWT
     @Schema(name = "Jwt Request",description = "Autenticación del usuario con email y contraseña")
     class JwtRequestSchema extends com.transportapi.model.jwt.JwtRequest{
    }

     @Schema(name = "Jwt Response",description = "Respuesta del token JWT")
     class JwtResponseSchema extends com.transportapi.model.jwt.JwtResponse{
    }

     @Schema(name = "User",description = "Usuario")
     class UserSchema extends com.transportapi.model.entity.User{
    }
}


    // Define una anotación para la respuesta de autenticación
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Autenticación exitosa",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Schemas.JwtRequestSchema.class)
            ))
    })
    public @interface AuthApiResponse {}

     // Define una anotación para el cuerpo de la solicitud de autenticación
    @RequestBody(
        description = "Autenticación de usuario con email y contraseña",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Schemas.JwtRequestSchema.class)
        )
    )
    public @interface AuthRequestBody {}

     // Define una anotación para el cuerpo de la solicitud de registro de usuario
     @RequestBody(
        description = "Registro de usuario",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Schemas.UserSchema.class)
        )
    )
    public @interface RegisterRequestBody {}

     // Define una anotación para las respuestas de usuario
     @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Operación exitosa",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Schemas.UserSchema.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class)
            )
        )
    })
    public @interface UserApiResponses {}

  
}
