package cl.biosur.usuarios;

import cl.biosur.usuarios.model.RolUsuario;
import cl.biosur.usuarios.model.Usuario;
import cl.biosur.usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Punto de entrada del microservicio de usuarios y autenticación corporativa de Biosur SpA.
 */
@SpringBootApplication
public class MsUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsuariosApplication.class, args);
    }

    /**
     * Carga usuarios semilla iniciales si la base de datos se encuentra vacía.
     */
    @Bean
    CommandLineRunner cargarUsuariosIniciales(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                usuarioRepository.saveAll(List.of(
                        new Usuario("fernando.barra@biosur.cl", "Fernando Barra Muñoz", RolUsuario.SUPERVISOR_TERRENO, true),
                        new Usuario("esteban.medina@biosur.cl", "Esteban Medina", RolUsuario.SUPERVISOR_TERRENO, true),
                        new Usuario("gilda.orellana@administracion.biosur.cl", "Gilda Orellana", RolUsuario.ADMINISTRADOR, true)
                ));
            }
        };
    }
}
