package com.mgcss.track.domain;

import java.time.LocalDateTime;

public class Solicitud {

    private Long id;
    private Estado estado;
    private LocalDateTime fechaCreacion;
    private Tecnico tecnicoAsignado;

    public enum Estado {
        ABIERTA, EN_PROCESO, CERRADA
    }

    // Constructor secundario para simular datos que vienen de la Base de Datos
    public Solicitud(Long id, Estado estadoInicial) {
        this.id = id;
        this.estado = estadoInicial;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Solicitud(Long id) {
        this.id = id;
        this.estado = Estado.ABIERTA; 
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Estado getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    public void cerrar() {
        if (this.estado != Estado.EN_PROCESO) {
            throw new IllegalStateException("Solo solicitudes en proceso pueden cerrarse");
        }
        this.estado = Estado.CERRADA;
    }


    public void asignarTecnico(Tecnico tecnico) {
        if (!tecnico.isActivo()) {
            throw new IllegalArgumentException("No se puede asignar un técnico inactivo a la solicitud");
        }
        this.tecnicoAsignado = tecnico;
    }


    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }
}