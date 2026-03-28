package com.mgcss.track.domain;

import java.time.LocalDateTime;

public class Solicitud {

    private Long id;
    private Estado estado;
    private LocalDateTime fechaCreacion;

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

   // Añade este método dentro de tu clase Solicitud
   
    public void cerrar() {
        if (this.estado != Estado.EN_PROCESO) {
            throw new IllegalStateException("Solo solicitudes en proceso pueden cerrarse");
        }
        this.estado = Estado.CERRADA;
    }
}