package com.omnibus.backend.service;

import com.omnibus.backend.model.Omnibus;
import com.omnibus.backend.model.Localidad;
import com.omnibus.backend.repository.OmnibusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OmnibusService {

    private final OmnibusRepository omnibusRepository;

    @Autowired
    public OmnibusService(OmnibusRepository omnibusRepository) {
        this.omnibusRepository = omnibusRepository;
    }

    public List<Omnibus> listarTodosLosOmnibus() {
        return omnibusRepository.findAll();
    }

    public Optional<Omnibus> obtenerOmnibusPorId(Long id) {
        return omnibusRepository.findById(id);
    }

    public Optional<Omnibus> obtenerOmnibusPorMatricula(String matricula) {
        return omnibusRepository.findByMatricula(matricula);
    }

    public List<Omnibus> obtenerOmnibusPorEstado(Omnibus.EstadoOmnibus estado) {
        return omnibusRepository.findByEstado(estado);
    }

    public List<Omnibus> obtenerOmnibusPorLocalidad(Localidad localidad) {
        return omnibusRepository.findByLocalidadActual(localidad);
    }

    public List<Omnibus> obtenerOmnibusPorModelo(String modelo) {
        return omnibusRepository.findByModelo(modelo);
    }

    public Omnibus guardarOmnibus(Omnibus omnibus) {
        return omnibusRepository.save(omnibus);
    }

    public void eliminarOmnibus(Long id) {
        omnibusRepository.deleteById(id);
    }

    public boolean actualizarEstadoOmnibus(Long id, Omnibus.EstadoOmnibus estado) {
        Optional<Omnibus> omnibusOpt = omnibusRepository.findById(id);
        if (omnibusOpt.isPresent()) {
            Omnibus omnibus = omnibusOpt.get();
            omnibus.setEstado(estado);
            omnibusRepository.save(omnibus);
            return true;
        }
        return false;
    }

    public boolean actualizarLocalidadOmnibus(Long id, Localidad nuevaLocalidad) {
        Optional<Omnibus> omnibusOpt = omnibusRepository.findById(id);
        if (omnibusOpt.isPresent()) {
            Omnibus omnibus = omnibusOpt.get();
            omnibus.setLocalidadActual(nuevaLocalidad);
            omnibusRepository.save(omnibus);
            return true;
        }
        return false;
    }

    public boolean existeOmnibusPorMatricula(String matricula) {
        return omnibusRepository.existsByMatricula(matricula);
    }

    public List<Omnibus> obtenerTodosOrdenadosPorMatricula() {
        return omnibusRepository.findAllByOrderByMatriculaAsc();
    }

}