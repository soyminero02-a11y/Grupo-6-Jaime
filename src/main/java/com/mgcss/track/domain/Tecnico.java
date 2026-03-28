package com.mgcss.track.domain;

public class Tecnico {
    private Long id;
    private boolean activo;

    public Tecnico(Long id, boolean activo) {
        this.id = id;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public boolean isActivo() { return activo; }
}