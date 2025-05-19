package com.omnibus.backend.controller;

import com.omnibus.backend.model.Administrador;
import com.omnibus.backend.model.Cliente;
import com.omnibus.backend.model.Usuario;
import com.omnibus.backend.model.Vendedor;
import com.omnibus.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Método para copiar los datos de un usuario a otro (Administrador, Vendedor, Cliente)
    private void copiarDatosUsuario(Usuario origen, Usuario destino) {
        destino.setNombre(origen.getNombre());
        destino.setApellido(origen.getApellido());
        destino.setCi(origen.getCi());
        destino.setContrasenia(origen.getContrasenia());
        destino.setEmail(origen.getEmail());
        destino.setTelefono(origen.getTelefono());
        destino.setFechaNac(origen.getFechaNac());
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Crear un Cliente a partir del objeto recibido
            Cliente cliente = new Cliente();
            cliente.setNombre(usuario.getNombre());
            cliente.setApellido(usuario.getApellido());
            cliente.setCi(usuario.getCi());
            cliente.setContrasenia(usuario.getContrasenia());
            cliente.setEmail(usuario.getEmail());
            cliente.setTelefono(usuario.getTelefono());
            cliente.setFechaNac(usuario.getFechaNac());

            Usuario nuevoUsuario = usuarioService.registrarUsuario(cliente);
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
            response.put("rol", usuario.getRol());
            response.put("mensaje", "Login exitoso");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuarioPorAdmin(@RequestBody Usuario usuario, @RequestParam Long adminId) {
        Optional<Usuario> adminOpt = usuarioService.obtenerPorId(adminId);
        if (adminOpt.isEmpty() || !"Administrador".equals(adminOpt.get().getRol())) {
            return ResponseEntity.status(403).body("Acceso denegado: solo administradores pueden crear usuarios.");
        }

        Usuario admin = adminOpt.get();  // Ahora tienes el Usuario correctamente

        Usuario nuevoUsuario;
        switch (usuario.getRol()) {
            case "Administrador":
                Administrador nuevoAdmin = new Administrador();
                copiarDatosUsuario(usuario, nuevoAdmin);
                nuevoUsuario = usuarioService.registrarUsuario(nuevoAdmin);
                break;
            case "Vendedor":
                Vendedor nuevoVendedor = new Vendedor();
                copiarDatosUsuario(usuario, nuevoVendedor);
                nuevoUsuario = usuarioService.registrarUsuario(nuevoVendedor);
                break;
            default:
                Cliente nuevoCliente = new Cliente();
                copiarDatosUsuario(usuario, nuevoCliente);
                nuevoUsuario = usuarioService.registrarUsuario(nuevoCliente);
                break;
        }

        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerTodosUsuarios(@RequestParam Long adminId) {
        // Verificar que el usuario es un administrador
        Optional<Usuario> adminOpt = usuarioService.obtenerPorId(adminId);
        if (adminOpt.isEmpty() || !"Administrador".equals(adminOpt.get().getRol())) {
            return ResponseEntity.status(403)
                    .body("Acceso denegado: solo los administradores pueden ver los usuarios.");
        }

        // Obtener la lista de todos los usuarios
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();

        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.status(404).body("No se encontraron usuarios.");
        }

        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarUsuario(@RequestParam Long usuarioId, @RequestParam Long adminId) {
        // Verificar que el usuario que hace la solicitud es un administrador
        Optional<Usuario> adminOpt = usuarioService.obtenerPorId(adminId);
        if (adminOpt.isEmpty() || !"Administrador".equals(adminOpt.get().getRol())) {
            return ResponseEntity.status(403)
                    .body("Acceso denegado: solo los administradores pueden eliminar usuarios.");
        }

        // Evitar que el admin se elimine a sí mismo
        if (usuarioId.equals(adminId)) {
            return ResponseEntity.status(400).body("No puedes eliminarte a ti mismo.");
        }

        // Buscar al usuario por su ID
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(usuarioId);
        if (usuarioOpt.isPresent()) {
            usuarioService.eliminarUsuario(usuarioOpt.get());
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        }

        return ResponseEntity.status(404).body("Usuario no encontrado.");
    }
    @PostMapping("/crear-masivo")
    public ResponseEntity<?> crearUsuariosDesdeCSV(@RequestParam("archivo") MultipartFile archivo,
                                                   @RequestParam("adminId") Long adminId) {
        Optional<Usuario> adminOpt = usuarioService.obtenerPorId(adminId);
        if (adminOpt.isEmpty() || !"Administrador".equals(adminOpt.get().getRol())) {
            return ResponseEntity.status(403).body("Acceso denegado: solo los administradores pueden subir archivos.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            String linea;
            List<Usuario> usuariosCreados = new ArrayList<>();

            // Saltar encabezado
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length < 8) continue;

                Usuario nuevoUsuario;
                String rol = datos[7].trim();

                switch (rol) {
                    case "Administrador":
                        nuevoUsuario = new Administrador();
                        break;
                    case "Vendedor":
                        nuevoUsuario = new Vendedor();
                        break;
                    case "Cliente": // Agregar este caso
                        nuevoUsuario = new Cliente();
                        break;
                    default:
                        continue;
                }

                nuevoUsuario.setNombre(datos[0].trim());
                nuevoUsuario.setApellido(datos[1].trim());
                nuevoUsuario.setCi(Integer.parseInt(datos[2].trim()));
                nuevoUsuario.setContrasenia(datos[3].trim());
                nuevoUsuario.setEmail(datos[4].trim());
                nuevoUsuario.setTelefono(Integer.parseInt(datos[5].trim()));
                nuevoUsuario.setFechaNac(LocalDate.parse(datos[6].trim()));

                usuarioService.registrarUsuario(nuevoUsuario);
                usuariosCreados.add(nuevoUsuario);
            }

            return ResponseEntity.ok("Usuarios cargados correctamente: " + usuariosCreados.size());

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al leer el archivo CSV: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al procesar el archivo: " + e.getMessage());
        }
    }

}

