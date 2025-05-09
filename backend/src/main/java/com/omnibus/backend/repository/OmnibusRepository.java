package com.omnibus.backend.repository;

import com.omnibus.backend.model.Omnibus;
import com.omnibus.backend.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OmnibusRepository extends JpaRepository<Omnibus, Long> {

    Optional<Omnibus> findByMatricula(String matricula);

    List<Omnibus> findByEstado(Omnibus.EstadoOmnibus estado);

    List<Omnibus> findByLocalidadActual(Localidad localidad);

    List<Omnibus> findByModelo(String modelo);

    boolean existsByMatricula(String matricula);

    List<Omnibus> findAllByOrderByMatriculaAsc();

}