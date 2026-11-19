package cl.biosur.usuarios.service;

import cl.biosur.usuarios.model.RolUsuario;
import cl.biosur.usuarios.model.Usuario;
import cl.biosur.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void resolverRolCorporativo_dominioBiosur_asignaSupervisorTerreno() {
        RolUsuario rol = usuarioService.resolverRolCorporativo("  Fernando.Barra@biosur.cl ");
        assertEquals(RolUsuario.SUPERVISOR_TERRENO, rol);
    }

    @Test
    void resolverRolCorporativo_dominioAdministracion_asignaAdministrador() {
        RolUsuario rol = usuarioService.resolverRolCorporativo("gilda.orellana@administracion.biosur.cl");
        assertEquals(RolUsuario.ADMINISTRADOR, rol);
    }

    @Test
    void resolverRolCorporativo_dominioNoAutorizado_lanzaExcepcionPorDefaultDeny() {
        assertThrows(IllegalArgumentException.class,
                () -> usuarioService.resolverRolCorporativo("intruso@gmail.com"));
    }

    @Test
    void resolverRolCorporativo_correoEnBlanco_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> usuarioService.resolverRolCorporativo("   "));
    }

    @Test
    void autenticarORegistrar_usuarioNuevoValido_guardaYRetornaSupervisor() {
        when(usuarioRepository.findById("nuevo.operario@biosur.cl")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        Usuario resultado = usuarioService.autenticarORegistrar("nuevo.operario@biosur.cl", null);

        assertEquals("nuevo.operario@biosur.cl", resultado.getEmail());
        assertEquals("Nuevo Operario", resultado.getNombre());
        assertEquals(RolUsuario.SUPERVISOR_TERRENO, resultado.getRol());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void autenticarORegistrar_cuentaDesactivada_lanzaIllegalStateException() {
        Usuario desactivado = new Usuario("baja@biosur.cl", "Ex Operario", RolUsuario.SUPERVISOR_TERRENO, false);
        when(usuarioRepository.findById("baja@biosur.cl")).thenReturn(Optional.of(desactivado));

        assertThrows(IllegalStateException.class,
                () -> usuarioService.autenticarORegistrar("baja@biosur.cl", null));
    }

    @Test
    void eliminarUsuario_existe_retornaTrue() {
        when(usuarioRepository.existsById("fernando.barra@biosur.cl")).thenReturn(true);

        boolean eliminado = usuarioService.eliminarUsuario("fernando.barra@biosur.cl");

        assertTrue(eliminado);
        verify(usuarioRepository).deleteById("fernando.barra@biosur.cl");
    }

    @Test
    void eliminarUsuario_noExiste_retornaFalse() {
        when(usuarioRepository.existsById("noexiste@biosur.cl")).thenReturn(false);

        assertFalse(usuarioService.eliminarUsuario("noexiste@biosur.cl"));
    }
}
