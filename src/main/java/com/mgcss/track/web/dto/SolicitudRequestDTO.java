package com.mgcss.track.web.dto;

import jakarta.validation.constraints.NotNull;

public record SolicitudRequestDTO(
    @NotNull(message = "El ID de la solicitud no puede ser nulo") Long id, 
    @NotNull(message = "El ID del cliente es obligatorio") Long clienteId,
    String descripcion
) {}




