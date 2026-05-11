package com.mgcss.track.web.dto;
import java.time.LocalDate;
import java.util.List;

public record SolicitudResponseDTO(Long id, String estado, String nombreTecnico, LocalDate FechaCreacion, List<String> Historial) {
    
}
