package com.mgcss.track.web.dto;

import jakarta.validation.constraints.NotNull;

public record SolicitudRequestDTO(
    @NotNull(message = "El ID no puede ser nulo") Long id, 
    String descripcion
) {}




