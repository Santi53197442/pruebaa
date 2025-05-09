package com.omnibus.backend.model;

import jakarta.persistence.*;


@Entity
@Table(name = "omnibus")
public class Omnibus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "cantidad_asientos", nullable = false)
    private Integer cantidadAsientos;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoOmnibus estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "localidad_id", nullable = false)
    private Localidad localidadActual;

    // Enumeración para el estado del ómnibus
    public enum EstadoOmnibus {
        ACTIVO, INACTIVO
    }

    // Constructores
    public Omnibus() {
    }

    public Omnibus(String matricula, String modelo, Integer cantidadAsientos,
                   EstadoOmnibus estado, Localidad localidadActual) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.cantidadAsientos = cantidadAsientos;
        this.estado = estado;
        this.localidadActual = localidadActual;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getCantidadAsientos() {
        return cantidadAsientos;
    }

    public void setCantidadAsientos(Integer cantidadAsientos) {
        this.cantidadAsientos = cantidadAsientos;
    }

    public EstadoOmnibus getEstado() {
        return estado;
    }

    public void setEstado(EstadoOmnibus estado) {
        this.estado = estado;
    }

    public Localidad getLocalidadActual() {
        return localidadActual;
    }

    public void setLocalidadActual(Localidad localidadActual) {
        this.localidadActual = localidadActual;
    }

    @Override
    public String toString() {
        return "Omnibus{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cantidadAsientos=" + cantidadAsientos +
                ", estado=" + estado +
                ", localidadActual=" + localidadActual +
                '}';
    }
}