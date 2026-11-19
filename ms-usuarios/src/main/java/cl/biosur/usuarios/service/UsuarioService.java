package cl.biosur.usuarios.service;

import cl.biosur.usuarios.model.RolUsuario;
import cl.biosur.usuarios.model.Usuario;
import cl.biosur.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Servicio de negocio para gestión de supervisores y validación corporativa de acceso.
 * Aplica denegación por defecto y asignación de rol por menor privilegio.
 */
@Service
public class UsuarioService {

    private static final Set<String> DOMINIOS_AUTORIZADOS = Set.of(
            "biosur.cl",
            "administracion.biosur.cl"
    );

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Valida el dominio del correo y asigna el rol correspondiente según menor privilegio.
     */
    public RolUsuario resolverRolCorporativo(String emailRaw) {
        if (emailRaw == null || emailRaw.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }
        String emailLimpio = emailRaw.trim().toLowerCase();
        String[] partes = emailLimpio.split("@");
        if (partes.length != 2 || partes[0].isBlank() || partes[1].isBlank()) {
            throw new IllegalArgumentException("Estructura de correo electrónico no válida");
        }
        String dominio = partes[1];
        if (!DOMINIOS_AUTORIZADOS.contains(dominio)) {
            throw new IllegalArgumentException("Acceso denegado por defecto: dominio @" + dominio + " no autorizado");
        }
        return "administracion.biosur.cl".equals(dominio)
                ? RolUsuario.ADMINISTRADOR
                : RolUsuario.SUPERVISOR_TERRENO;
    }

    /**
     * Autentica un usuario por correo corporativo; si no existe aún en base de datos, lo registra.
     */
    public Usuario autenticarORegistrar(String emailRaw, String nombreOpcional) {
        RolUsuario rolAsignado = resolverRolCorporativo(emailRaw);
        String emailLimpio = emailRaw.trim().toLowerCase();

        Optional<Usuario> existente = usuarioRepository.findById(emailLimpio);
        if (existente.isPresent()) {
            Usuario usuario = existente.get();
            if (!usuario.isActivo()) {
                throw new IllegalStateException("La cuenta corporativa se encuentra desactivada");
            }
            return usuario;
        }

        String nombreFinal = (nombreOpcional != null && !nombreOpcional.isBlank())
                ? nombreOpcional.trim()
                : formatearNombreDesdeCorreo(emailLimpio);

        Usuario nuevoUsuario = new Usuario(emailLimpio, nombreFinal, rolAsignado, true);
        return usuarioRepository.save(nuevoUsuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarPorRol(RolUsuario rol) {
        return usuarioRepository.findByRol(rol);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return usuarioRepository.findById(email.trim().toLowerCase());
    }

    public Usuario guardarUsuario(Usuario usuario) {
        RolUsuario rolResuelto = resolverRolCorporativo(usuario.getEmail());
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());
        usuario.setRol(rolResuelto);
        return usuarioRepository.save(usuario);
    }

    public boolean eliminarUsuario(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        String emailLimpio = email.trim().toLowerCase();
        if (!usuarioRepository.existsById(emailLimpio)) {
            return false;
        }
        usuarioRepository.deleteById(emailLimpio);
        return true;
    }

    private String formatearNombreDesdeCorreo(String emailLimpio) {
        String localPart = emailLimpio.split("@")[0].replace(".", " ");
        String[] palabras = localPart.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : palabras) {
            if (!p.isBlank()) {
                sb.append(Character.toUpperCase(p.charAt(0)))
                  .append(p.substring(1))
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }
}
