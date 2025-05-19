package com.omnibus.backend.controller;

import com.omnibus.backend.dto.CrearViajeDTO;
import com.omnibus.backend.model.Viaje;
import com.omnibus.backend.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viajes")
@CrossOrigin(origins = "http://localhost:3000")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearViaje(@RequestBody CrearViajeDTO dto) {
        try {
            Viaje viaje = viajeService.crearViaje(
                    dto.getFecha(),
                    dto.getHoraSalida(),
                    dto.getHoraLlegada(),
                    dto.getOrigenId(),
                    dto.getDestinoId(),
                    dto.getBusId()
            );
            return ResponseEntity.ok(viaje);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
