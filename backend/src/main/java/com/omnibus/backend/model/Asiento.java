package com.omnibus.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asientos")
public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Omnibus (muchos asientos pertenecen a un ómnibus)
    @ManyToOne(optional = false)
    @JoinColumn(name = "omnibus_id")
    private Omnibus omnibus;

    @Column(nullable = false)
    private boolean ocupado;

    @Column(nullable = false)
    private int posAsiento;

    // Constructor vacío
    public Asiento() {}

    // Constructor con validación de posición
    public Asiento(Omnibus omnibus, boolean ocupado, int posAsiento) {
        if (omnibus != null && posAsiento > omnibus.getCantidadAsientos()) {
            throw new IllegalArgumentException("La posición del asiento excede la capacidad del ómnibus");
        }
        this.omnibus = omnibus;
        this.ocupado = ocupado;
        this.posAsiento = posAsiento;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public Omnibus getOmnibus() {
        return omnibus;
    }

    public void setOmnibus(Omnibus omnibus) {
        this.omnibus = omnibus;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getPosAsiento() {
        return posAsiento;
    }

    public void setPosAsiento(int posAsiento) {
        if (omnibus != null && posAsiento > omnibus.getCantidadAsientos()) {
            throw new IllegalArgumentException("La posición del asiento excede la capacidad del ómnibus");
        }
        this.posAsiento = posAsiento;
    }
}
