package com.mgcss.track.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Objeto de transferencia de datos utilizado para la creación de una nueva solicitud de servicio")
public record SolicitudRequestDTO(
    
    @Schema(description = "Identificador único de la solicitud", example = "1")
    @NotNull(message = "El ID de la solicitud no puede ser nulo") 
    Long id, 
    
    @Schema(description = "Identificador del cliente que registra la incidencia", example = "100")
    @NotNull(message = "El ID del cliente es obligatorio") 
    Long clienteId,
    
    @Schema(description = "Descripción detallada del problema o servicio requerido", example = "Pantalla rota en el equipo principal")
    String descripcion
) {}