package com.mgcss.track.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Cliente {

    @Id
    private Long id;
    private String nombre;
    private String email;
    
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    public enum TipoCliente {
        STANDARD, PREMIUM
    }

    protected Cliente() {}

    public Cliente(Long id, String nombre, String email, TipoCliente tipoCliente) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipoCliente = tipoCliente;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public TipoCliente getTipoCliente() { return tipoCliente; }
}