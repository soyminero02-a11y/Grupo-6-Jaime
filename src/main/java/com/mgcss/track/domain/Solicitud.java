package com.mgcss.track.domain;

import java.time.LocalDateTime;

public class Solicitud {
    
    private Long id;
    private Estado estado;
    private LocalDateTime fechaCreacion;

    // Enum básico para el estado, basado en las reglas de negocio
    public enum Estado {
        ABIERTA, EN_PROCESO, CERRADA
    }

    
    public Solicitud(Long id) {
        this.id = id;
        this.estado = Estado.ABIERTA; // Estado inicial por defecto
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public Estado getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    
    // Setters (solo los necesarios para el comportamiento)
    public void setEstado(Estado estado) { this.estado = estado; }
}