package com.mgcss.track.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Solicitud {

    @Id
    private Long id;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "solicitud_historial", joinColumns = @JoinColumn(name = "solicitud_id"))
    private List<Estado> historialEstados = new ArrayList<>();

    @ManyToOne 
    private Cliente cliente;
    
    @ManyToOne 
    private Tecnico tecnicoAsignado;

    private LocalDate fechaCreacion; 
    private LocalDate fechaCierre; 

    protected Solicitud() {
    }

   public Solicitud(Long id,String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = Estado.ABIERTA;
        this.fechaCreacion = LocalDate.now();
        registrarCambioEstado();
    }

    public Solicitud(Long id, String descripcion, Estado estadoInicial) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estadoInicial;
        this.fechaCreacion = LocalDate.now();
        registrarCambioEstado();
    }

    public Solicitud(Long id, Cliente cliente, String descripcion) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.estado = Estado.ABIERTA;
        this.fechaCreacion = LocalDate.now();
        registrarCambioEstado();
    }

    public Solicitud(Long id, Cliente cliente, String descripcion, Estado estadoInicial) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.estado = estadoInicial;
        this.fechaCreacion = LocalDate.now();
        registrarCambioEstado();
    }

    public enum Estado {
        ABIERTA, EN_PROCESO, CERRADA 
    }

    public void cerrar() {
        if (this.estado != Estado.EN_PROCESO) {
            throw new IllegalStateException("Solo se puede cerrar si está en proceso"); 
        }
        this.estado = Estado.CERRADA;
        this.fechaCierre = LocalDate.now(); 
        registrarCambioEstado();
    }

    public void reabrir() {
        if (this.estado != Estado.CERRADA) {
            throw new IllegalStateException("Solo se pueden reabrir solicitudes que están cerradas"); 
        }
        this.estado = Estado.EN_PROCESO;
        this.fechaCierre = null;
        registrarCambioEstado();
    }

    public void asignarTecnico(Tecnico tecnico) {
        validarTecnicoActivo(tecnico);
        this.tecnicoAsignado = tecnico;
    }

    private void validarTecnicoActivo(Tecnico tecnico) {
        if (!tecnico.isActivo()) {
            throw new IllegalArgumentException("No se puede asignar un técnico inactivo");
        }
    }

    private void registrarCambioEstado() {
        this.historialEstados.add(this.estado);
    }

    public Long getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public Estado getEstado() { return estado; }
    public Cliente getCliente() { return cliente; }
    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public LocalDate getFechaCierre() { return fechaCierre; }
    public List<Estado> getHistorialEstados() { return new ArrayList<>(historialEstados); }
}