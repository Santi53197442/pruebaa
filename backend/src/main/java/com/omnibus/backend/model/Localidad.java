package com.omnibus.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;



@Entity
@Table(name = "localidades")
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    // Constructores
    public Localidad() {
    }

    public Localidad(String nombre, String departamento) {
        this.nombre = nombre;
        this.departamento = departamento;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Localidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}