package com.omnibus.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendedores")
public class Vendedor extends Usuario {
    public Vendedor() {
        super();
        setRol("Vendedor");
    }
}
