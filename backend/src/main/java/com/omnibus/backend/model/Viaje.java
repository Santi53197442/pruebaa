package com.omnibus.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "viajes")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime horaSalida;

    @Column(nullable = false)
    private LocalTime horaLlegada;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origen_id")
    private Localidad origen;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destino_id")
    private Localidad destino;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bus_id")
    private Omnibus busAsignado;

    // Constructores
    public Viaje() {
    }

    public Viaje(LocalDate fecha, LocalTime horaSalida, LocalTime horaLlegada,
                 Localidad origen, Localidad destino, Omnibus busAsignado) {
        this.fecha = fecha;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.origen = origen;
        this.destino = destino;
        this.busAsignado = busAsignado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Localidad getOrigen() {
        return origen;
    }

    public void setOrigen(Localidad origen) {
        this.origen = origen;
    }

    public Localidad getDestino() {
        return destino;
    }

    public void setDestino(Localidad destino) {
        this.destino = destino;
    }

    public Omnibus getBusAsignado() {
        return busAsignado;
    }

    public void setBusAsignado(Omnibus busAsignado) {
        this.busAsignado = busAsignado;
    }
}
