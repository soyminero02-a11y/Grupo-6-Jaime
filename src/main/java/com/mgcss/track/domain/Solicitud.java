package com.mgcss.track.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient; 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Solicitud {

    @Id
    private Long id;
    private Estado estado;
    private List<Estado> historialEstados = new ArrayList<>();
    
    @Transient 
    private Tecnico tecnicoAsignado;
    private LocalDate fechaCreacion; 

    protected Solicitud() {
    }

    public Solicitud(Long id, Estado estadoInicial) {
        this.id = id;
        this.estado = estadoInicial;
        registrarCambioEstado();
        this.fechaCreacion = LocalDate.now();
    }
    
    public Solicitud(Long id) {
        this.id = id;
        this.estado = Estado.ABIERTA;
        registrarCambioEstado();
        this.fechaCreacion = LocalDate.now();
    }

    public enum Estado {
        ABIERTA, EN_PROCESO, CERRADA 
    }

    public void cerrar() {
        if (this.estado != Estado.EN_PROCESO) {
            throw new IllegalStateException("Solo se puede cerrar si está en proceso");
        }
        this.estado = Estado.CERRADA;
        registrarCambioEstado();
    }

    public void asignarTecnico(Tecnico tecnico) {
        validarTecnicoActivo(tecnico); 
        this.tecnicoAsignado = tecnico;
    }

    
    private void validarTecnicoActivo(Tecnico tecnico) {
        if (!tecnico.isActivo()) {
            throw new IllegalArgumentException("No se puede asignar un técnico inactivo a la solicitud");
        }
    }

    private void registrarCambioEstado() {
        this.historialEstados.add(this.estado);
    }

    public Estado getEstado() {
        return estado;
    }

    public Long getId() {
        return id;
    }

    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void reabrir() {
        if (this.estado != Estado.CERRADA) {
            throw new IllegalStateException("Solo se pueden reabrir solicitudes que están cerradas");
        }
        this.estado = Estado.EN_PROCESO;
        registrarCambioEstado();
    }

    public List<Estado> getHistorialEstados() {
        return this.historialEstados;
    }
}