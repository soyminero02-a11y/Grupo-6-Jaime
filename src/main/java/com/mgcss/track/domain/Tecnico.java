package com.mgcss.track.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tecnico {

    @Id
    private Long id;
    private String nombre;
    private String especialidad;
    private boolean activo;

    protected Tecnico() {}

    public Tecnico(Long id, boolean activo) {
        this.id = id;
        this.activo = activo;
    }

    public Tecnico(Long id, String nombre, String especialidad, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
    public boolean isActivo() { return activo; }
}