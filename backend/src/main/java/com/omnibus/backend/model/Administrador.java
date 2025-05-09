package com.omnibus.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores")
public class Administrador extends Usuario {
    public Administrador() {
        super();
        setRol("Administrador");
    }
}
