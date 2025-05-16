package com.omnibus.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrearViajeDTO {
    private LocalDate fecha;
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    private Long origenId;
    private Long destinoId;
    private Long busId;

    // Constructores
    public CrearViajeDTO() {}

    public CrearViajeDTO(LocalDate fecha, LocalTime horaSalida, LocalTime horaLlegada,
                         Long origenId, Long destinoId, Long busId) {
        this.fecha = fecha;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.origenId = origenId;
        this.destinoId = destinoId;
        this.busId = busId;
    }

    // Getters y Setters
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

    public Long getOrigenId() {
        return origenId;
    }

    public void setOrigenId(Long origenId) {
        this.origenId = origenId;
    }

    public Long getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }
}
