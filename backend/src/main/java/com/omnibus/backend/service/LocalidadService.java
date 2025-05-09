package com.omnibus.backend.service;

import com.omnibus.backend.model.Localidad;
import com.omnibus.backend.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalidadService {

    private final LocalidadRepository localidadRepository;

    @Autowired
    public LocalidadService(LocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }

    public List<Localidad> listarTodasLasLocalidades() {
        return localidadRepository.findAll();
    }

    public Optional<Localidad> obtenerLocalidadPorId(Long id) {
        return localidadRepository.findById(id);
    }

    public Optional<Localidad> obtenerLocalidadPorNombre(String nombre) {
        return localidadRepository.findByNombre(nombre);
    }

    public List<Localidad> obtenerLocalidadesPorDepartamento(String departamento) {
        return localidadRepository.findByDepartamento(departamento);
    }

    public Localidad guardarLocalidad(Localidad localidad) {
        return localidadRepository.save(localidad);
    }

    public void eliminarLocalidad(Long id) {
        localidadRepository.deleteById(id);
    }

    public boolean existeLocalidad(String nombre, String departamento) {
        return localidadRepository.existsByNombreAndDepartamento(nombre, departamento);
    }
}