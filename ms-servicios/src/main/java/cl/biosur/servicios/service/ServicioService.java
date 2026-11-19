package cl.biosur.servicios.service;

import cl.biosur.servicios.model.EstadoServicio;
import cl.biosur.servicios.model.Servicio;
import cl.biosur.servicios.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión integral (CRUD) de faenas de Biosur SpA.
 */
@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public List<Servicio> listarPorSupervisor(String supervisor) {
        if (supervisor == null || supervisor.isBlank()) {
            return listarTodos();
        }
        return servicioRepository.findBySupervisorContainingIgnoreCase(supervisor.trim());
    }

    public List<Servicio> listarPorEstado(EstadoServicio estado) {
        return servicioRepository.findByEstado(estado);
    }

    public Optional<Servicio> buscarPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio crear(Servicio servicio) {
        validarReglasNegocio(servicio);
        servicio.setId(null); // Fuerza autogeneración de clave primaria al insertar
        servicio.setDescripcion(servicio.getDescripcion().trim());
        servicio.setSupervisor(servicio.getSupervisor().trim());
        return servicioRepository.save(servicio);
    }

    public Optional<Servicio> actualizar(Long id, Servicio datosActualizados) {
        validarReglasNegocio(datosActualizados);
        return servicioRepository.findById(id).map(existente -> {
            existente.setFecha(datosActualizados.getFecha());
            existente.setHoraInicio(datosActualizados.getHoraInicio());
            existente.setHoraTermino(datosActualizados.getHoraTermino());
            existente.setSupervisor(datosActualizados.getSupervisor().trim());
            existente.setTipoServicio(datosActualizados.getTipoServicio());
            existente.setDescripcion(datosActualizados.getDescripcion().trim());
            existente.setHorasTrabajadas(datosActualizados.getHorasTrabajadas());
            existente.setCostoEstimado(datosActualizados.getCostoEstimado());
            existente.setLatitud(datosActualizados.getLatitud());
            existente.setLongitud(datosActualizados.getLongitud());
            existente.setFotoAntesUri(datosActualizados.getFotoAntesUri());
            existente.setFotoDespuesUri(datosActualizados.getFotoDespuesUri());
            existente.setEstado(datosActualizados.getEstado());
            return servicioRepository.save(existente);
        });
    }

    public boolean eliminar(Long id) {
        if (!servicioRepository.existsById(id)) {
            return false;
        }
        servicioRepository.deleteById(id);
        return true;
    }

    private void validarReglasNegocio(Servicio servicio) {
        if (servicio.getDescripcion() == null || servicio.getDescripcion().isBlank()
                || servicio.getDescripcion().trim().length() < 10) {
            throw new IllegalArgumentException("La descripción técnica debe tener al menos 10 caracteres");
        }
        if (servicio.getHorasTrabajadas() <= 0.0 || servicio.getHorasTrabajadas() > 24.0) {
            throw new IllegalArgumentException("Las horas trabajadas deben ubicarse entre 0.1 y 24.0 horas");
        }
        if (servicio.getCostoEstimado() < 0.0) {
            throw new IllegalArgumentException("El costo estimado no puede ser negativo");
        }
        if (servicio.getSupervisor() == null || servicio.getSupervisor().isBlank()) {
            throw new IllegalArgumentException("El nombre del supervisor responsable es obligatorio");
        }
    }
}
