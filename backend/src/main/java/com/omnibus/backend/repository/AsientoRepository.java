package com.omnibus.backend.repository;

import com.omnibus.backend.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {
    // Podés agregar métodos personalizados si los necesitás
}
