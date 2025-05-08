package com.omnibus.backend.service;

import com.omnibus.backend.model.Usuario;
import com.omnibus.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registrar un nuevo usuario
    public Usuario registrarUsuario(Usuario usuario) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Verificar si la CI ya existe
        if (usuarioRepository.existsByCi(usuario.getCi())) {
            throw new RuntimeException("La CI ya está registrada");
        }

        // Encriptar contraseña
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));

        return usuarioRepository.save(usuario);
    }

    // Autenticar usuario
    public Usuario autenticarUsuario(String email, String contrasenia) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(contrasenia, usuario.getContrasenia())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }
}