package com.omnibus.backend.repository;

import com.omnibus.backend.model.Usuario;
import com.omnibus.backend.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("SELECT v FROM Viaje v WHERE v.busAsignado.id = :busId AND " +
            "(:horaInicio < v.horaLlegada AND :horaFin > v.horaSalida) AND v.fecha = :fecha")
    List<Viaje> findConflictingViajes(@Param("busId") Long busId,
                                      @Param("fecha") LocalDate fecha,
                                      @Param("horaInicio") LocalTime horaInicio,
                                      @Param("horaFin") LocalTime horaFin);
}
