package cl.biosur.servicios.repository;

import cl.biosur.servicios.model.EstadoServicio;
import cl.biosur.servicios.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para persistencia remota de órdenes de servicio en terreno.
 */
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findBySupervisorContainingIgnoreCase(String supervisor);

    List<Servicio> findByEstado(EstadoServicio estado);
}
