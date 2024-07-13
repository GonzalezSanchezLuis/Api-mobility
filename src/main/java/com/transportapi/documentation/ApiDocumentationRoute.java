package com.transportapi.documentation;

import com.transportapi.model.entity.Route;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public class ApiDocumentationRoute {
    public interface Schemas{
        @Schema(name = "Route", description = "Controlador de rutas")
        class RouteSchema extends Route {}
    }

    @RequestBody(
        description = "Registro de ruta del usuario",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Schemas.RouteSchema.class)
        ))
    public @interface RegisterRequestBody {}

    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Operación exitosa",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Schemas.RouteSchema.class)
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
    public @interface RouteApiResponses {}
}
