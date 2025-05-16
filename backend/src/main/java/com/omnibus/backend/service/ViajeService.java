package com.omnibus.backend.service;

import com.omnibus.backend.model.Asiento;
import com.omnibus.backend.model.Localidad;
import com.omnibus.backend.model.Omnibus;
import com.omnibus.backend.model.Viaje;
import com.omnibus.backend.repository.AsientoRepository;
import com.omnibus.backend.repository.LocalidadRepository;
import com.omnibus.backend.repository.OmnibusRepository;
import com.omnibus.backend.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private OmnibusRepository omnibusRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private AsientoRepository asientoRepository;

    public Viaje crearViaje(LocalDate fecha, LocalTime horaSalida, LocalTime horaLlegada,
                            Long origenId, Long destinoId, Long busId) {
        Localidad origen = localidadRepository.findById(origenId)
                .orElseThrow(() -> new RuntimeException("Origen no encontrado"));
        Localidad destino = localidadRepository.findById(destinoId)
                .orElseThrow(() -> new RuntimeException("Destino no encontrado"));
        Omnibus omnibus = omnibusRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));

        // Verificar conflictos
        List<Viaje> conflictos = viajeRepository.findConflictingViajes(busId, fecha, horaSalida, horaLlegada);
        if (!conflictos.isEmpty()) {
            throw new RuntimeException("El ómnibus no está disponible en ese horario");
        }

        Viaje nuevoViaje = new Viaje();
        nuevoViaje.setFecha(fecha);
        nuevoViaje.setHoraSalida(horaSalida);
        nuevoViaje.setHoraLlegada(horaLlegada);
        nuevoViaje.setOrigen(origen);
        nuevoViaje.setDestino(destino);
        nuevoViaje.setBusAsignado(omnibus);

        // Guardar el viaje
        Viaje viajeGuardado = viajeRepository.save(nuevoViaje);

        // Crear los asientos
        for (int i = 1; i <= omnibus.getCantidadAsientos(); i++) {
            Asiento asiento = new Asiento();
            asiento.setOcupado(false);
            asiento.setOmnibus(omnibus);
            asiento.setPosAsiento(i);
            asientoRepository.save(asiento);
        }

        return viajeGuardado;
    }
}

