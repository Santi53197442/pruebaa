package com.omnibus.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {
    public Cliente() {
        super();
        setRol("Cliente");
    }
}
