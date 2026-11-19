package cl.biosur.usuarios.repository;

import cl.biosur.usuarios.model.RolUsuario;
import cl.biosur.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para persistencia y consulta de usuarios corporativos en H2.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    List<Usuario> findByRol(RolUsuario rol);

    List<Usuario> findByActivoTrue();
}
