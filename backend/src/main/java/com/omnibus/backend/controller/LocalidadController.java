package com.omnibus.backend.controller;

import com.omnibus.backend.model.Localidad;
import com.omnibus.backend.service.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/localidades")
@CrossOrigin(origins = "http://localhost:3000")
public class LocalidadController {

    private final LocalidadService localidadService;

    @Autowired
    public LocalidadController(LocalidadService localidadService) {
        this.localidadService = localidadService;
    }

    @GetMapping
    public ResponseEntity<List<Localidad>> listarLocalidades() {
        List<Localidad> localidades = localidadService.listarTodasLasLocalidades();
        return new ResponseEntity<>(localidades, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Localidad> obtenerLocalidadPorId(@PathVariable Long id) {
        Optional<Localidad> localidad = localidadService.obtenerLocalidadPorId(id);
        return localidad
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Localidad> obtenerLocalidadPorNombre(@PathVariable String nombre) {
        Optional<Localidad> localidad = localidadService.obtenerLocalidadPorNombre(nombre);
        return localidad
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<Localidad>> obtenerLocalidadesPorDepartamento(@PathVariable String departamento) {
        List<Localidad> localidades = localidadService.obtenerLocalidadesPorDepartamento(departamento);
        return new ResponseEntity<>(localidades, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Localidad> crearLocalidad(@RequestBody Localidad localidad) {
        if (localidadService.existeLocalidad(localidad.getNombre(), localidad.getDepartamento())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Localidad nuevaLocalidad = localidadService.guardarLocalidad(localidad);
        return new ResponseEntity<>(nuevaLocalidad, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Localidad> actualizarLocalidad(@PathVariable Long id, @RequestBody Localidad localidad) {
        Optional<Localidad> localidadExistente = localidadService.obtenerLocalidadPorId(id);
        if (localidadExistente.isPresent()) {
            localidad.setId(id);
            Localidad localidadActualizada = localidadService.guardarLocalidad(localidad);
            return new ResponseEntity<>(localidadActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLocalidad(@PathVariable Long id) {
        Optional<Localidad> localidad = localidadService.obtenerLocalidadPorId(id);
        if (localidad.isPresent()) {
            localidadService.eliminarLocalidad(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}