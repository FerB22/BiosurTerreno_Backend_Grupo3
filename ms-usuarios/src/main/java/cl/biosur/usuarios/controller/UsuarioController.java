package cl.biosur.usuarios.controller;

import cl.biosur.usuarios.dto.LoginRequest;
import cl.biosur.usuarios.model.RolUsuario;
import cl.biosur.usuarios.model.Usuario;
import cl.biosur.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión y autenticación de usuarios de Biosur Terreno.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Valida las credenciales corporativas del usuario y devuelve su perfil y rol asignado.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.autenticarORegistrar(request.getEmail(), request.getNombre());
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Lista todos los usuarios o filtra por rol si se envía el parámetro opcional.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listar(@RequestParam(required = false) RolUsuario rol) {
        List<Usuario> resultado = (rol != null)
                ? usuarioService.listarPorRol(rol)
                : usuarioService.listarTodos();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> obtenerPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearOActualizar(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario guardado = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> eliminar(@PathVariable String email) {
        if (usuarioService.eliminarUsuario(email)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
