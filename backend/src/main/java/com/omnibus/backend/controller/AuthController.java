package com.omnibus.backend.controller;

import com.omnibus.backend.model.Usuario;
import com.omnibus.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Para React frontend
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String contrasenia = loginRequest.get("contrasenia");

            Usuario usuario = usuarioService.autenticarUsuario(email, contrasenia);

            Map<String, Object> response = new HashMap<>();
            response.put("id", usuario.getId());
            response.put("nombre", usuario.getNombre());
            response.put("apellido", usuario.getApellido());
            response.put("email", usuario.getEmail());
            response.put("mensaje", "Login exitoso");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}