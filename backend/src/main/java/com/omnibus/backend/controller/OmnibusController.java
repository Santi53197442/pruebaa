package com.omnibus.backend.controller;

import com.omnibus.backend.model.Omnibus;
import com.omnibus.backend.model.Localidad;
import com.omnibus.backend.service.OmnibusService;
import com.omnibus.backend.service.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/omnibus")
@CrossOrigin(origins = "http://localhost:3000")
public class OmnibusController {

    private final OmnibusService omnibusService;
    private final LocalidadService localidadService;

    @Autowired
    public OmnibusController(OmnibusService omnibusService, LocalidadService localidadService) {
        this.omnibusService = omnibusService;
        this.localidadService = localidadService;
    }

    @GetMapping
    public ResponseEntity<List<Omnibus>> listarOmnibus() {
        List<Omnibus> omnibus = omnibusService.listarTodosLosOmnibus();
        return new ResponseEntity<>(omnibus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Omnibus> obtenerOmnibusPorId(@PathVariable Long id) {
        Optional<Omnibus> omnibus = omnibusService.obtenerOmnibusPorId(id);
        return omnibus
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Omnibus> obtenerOmnibusPorMatricula(@PathVariable String matricula) {
        Optional<Omnibus> omnibus = omnibusService.obtenerOmnibusPorMatricula(matricula);
        return omnibus
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Omnibus>> obtenerOmnibusPorEstado(@PathVariable Omnibus.EstadoOmnibus estado) {
        List<Omnibus> omnibus = omnibusService.obtenerOmnibusPorEstado(estado);
        return new ResponseEntity<>(omnibus, HttpStatus.OK);
    }

    @GetMapping("/localidad/{localidadId}")
    public ResponseEntity<List<Omnibus>> obtenerOmnibusPorLocalidad(@PathVariable Long localidadId) {
        Optional<Localidad> localidad = localidadService.obtenerLocalidadPorId(localidadId);
        if (localidad.isPresent()) {
            List<Omnibus> omnibus = omnibusService.obtenerOmnibusPorLocalidad(localidad.get());
            return new ResponseEntity<>(omnibus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<Omnibus>> obtenerOmnibusPorModelo(@PathVariable String modelo) {
        List<Omnibus> omnibus = omnibusService.obtenerOmnibusPorModelo(modelo);
        return new ResponseEntity<>(omnibus, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Omnibus> crearOmnibus(@RequestBody Omnibus omnibus) {
        // Verificar si la matrícula ya existe
        if (omnibusService.existeOmnibusPorMatricula(omnibus.getMatricula())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Verificar si la localidad existe
        Optional<Localidad> localidad = localidadService.obtenerLocalidadPorId(omnibus.getLocalidadActual().getId());
        if (localidad.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Localidad no encontrada
        }

        // Establecer la localidad real
        omnibus.setLocalidadActual(localidad.get());

        Omnibus nuevoOmnibus = omnibusService.guardarOmnibus(omnibus);
        return new ResponseEntity<>(nuevoOmnibus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Omnibus> actualizarOmnibus(@PathVariable Long id, @RequestBody Omnibus omnibus) {
        Optional<Omnibus> omnibusExistente = omnibusService.obtenerOmnibusPorId(id);
        if (omnibusExistente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verificar si la localidad existe
        Optional<Localidad> localidad = localidadService.obtenerLocalidadPorId(omnibus.getLocalidadActual().getId());
        if (localidad.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Verificar que no exista otro ómnibus con la misma matrícula (excepto el actual)
        Optional<Omnibus> omnibusConMismaMatricula = omnibusService.obtenerOmnibusPorMatricula(omnibus.getMatricula());
        if (omnibusConMismaMatricula.isPresent() && !omnibusConMismaMatricula.get().getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Establecer la localidad real y el ID
        omnibus.setLocalidadActual(localidad.get());
        omnibus.setId(id);

        Omnibus omnibusActualizado = omnibusService.guardarOmnibus(omnibus);
        return new ResponseEntity<>(omnibusActualizado, HttpStatus.OK);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstadoOmnibus(@PathVariable Long id, @RequestBody Omnibus.EstadoOmnibus estado) {
        boolean actualizado = omnibusService.actualizarEstadoOmnibus(id, estado);
        if (actualizado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/localidad/{localidadId}")
    public ResponseEntity<Void> actualizarLocalidadOmnibus(@PathVariable Long id, @PathVariable Long localidadId) {
        Optional<Localidad> localidad = localidadService.obtenerLocalidadPorId(localidadId);
        if (localidad.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean actualizado = omnibusService.actualizarLocalidadOmnibus(id, localidad.get());
        if (actualizado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOmnibus(@PathVariable Long id) {
        Optional<Omnibus> omnibus = omnibusService.obtenerOmnibusPorId(id);
        if (omnibus.isPresent()) {
            omnibusService.eliminarOmnibus(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<Omnibus>> listarOmnibusOrdenados() {
        List<Omnibus> omnibus = omnibusService.obtenerTodosOrdenadosPorMatricula();
        return new ResponseEntity<>(omnibus, HttpStatus.OK);
    }

}