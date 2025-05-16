package com.omnibus.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pasajes")
public class Pasaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el bus asignado (Omnibus)
    @ManyToOne(optional = false)
    @JoinColumn(name = "bus_id")
    private Omnibus busAsignado;

    // Relación con el asiento asignado
    @ManyToOne(optional = false)
    @JoinColumn(name = "asiento_id")
    private Asiento asientoAsignado;

    // Relación con el cliente (usuario)
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @Column(nullable = false)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPasaje estado;

    // Constructor vacío
    public Pasaje() {
    }

    // Constructor completo
    public Pasaje(Omnibus busAsignado, Asiento asientoAsignado, Usuario cliente, BigDecimal precio, EstadoPasaje estado) {
        this.busAsignado = busAsignado;
        this.asientoAsignado = asientoAsignado;
        this.cliente = cliente;
        this.precio = precio;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Omnibus getBusAsignado() {
        return busAsignado;
    }

        public void setBusAsignado(Omnibus busAsignado) {
        this.busAsignado = busAsignado;
    }

    public Asiento getAsientoAsignado() {
        return asientoAsignado;
    }

    public void setAsientoAsignado(Asiento asientoAsignado) {
        this.asientoAsignado = asientoAsignado;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public EstadoPasaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoPasaje estado) {
        this.estado = estado;
    }
}
