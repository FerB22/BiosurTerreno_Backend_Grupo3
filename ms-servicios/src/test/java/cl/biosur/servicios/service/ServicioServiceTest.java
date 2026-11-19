package cl.biosur.servicios.service;

import cl.biosur.servicios.model.EstadoServicio;
import cl.biosur.servicios.model.Servicio;
import cl.biosur.servicios.model.TipoServicio;
import cl.biosur.servicios.repository.ServicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    private ServicioService servicioService;

    @BeforeEach
    void setUp() {
        servicioService = new ServicioService(servicioRepository);
    }

    private Servicio crearServicioValido() {
        return new Servicio(
                "19/11/2026", "08:00", "12:00",
                "Fernando Barra",
                TipoServicio.OBRA_CIVIL,
                "Reparación estructural de cámara recolectora en faena",
                4.0, 150000.0,
                -37.50, -72.67,
                null, null,
                EstadoServicio.FINALIZADO
        );
    }

    @Test
    void crear_datosValidos_guardaConIdNuloParaAutogeneracion() {
        Servicio s = crearServicioValido();
        s.setId(99L);
        when(servicioRepository.save(any(Servicio.class))).thenAnswer(inv -> inv.getArgument(0));

        Servicio resultado = servicioService.crear(s);

        assertNull(resultado.getId());
        assertEquals("Fernando Barra", resultado.getSupervisor());
        verify(servicioRepository).save(any(Servicio.class));
    }

    @Test
    void crear_descripcionCorta_lanzaExcepcion() {
        Servicio s = crearServicioValido();
        s.setDescripcion("Corto");

        assertThrows(IllegalArgumentException.class, () -> servicioService.crear(s));
    }

    @Test
    void crear_horasFueraDeRango_lanzaExcepcion() {
        Servicio s = crearServicioValido();
        s.setHorasTrabajadas(25.0);

        assertThrows(IllegalArgumentException.class, () -> servicioService.crear(s));
    }

    @Test
    void crear_costoNegativo_lanzaExcepcion() {
        Servicio s = crearServicioValido();
        s.setCostoEstimado(-100.0);

        assertThrows(IllegalArgumentException.class, () -> servicioService.crear(s));
    }

    @Test
    void actualizar_idExistente_actualizaCamposYGuarda() {
        Servicio existente = crearServicioValido();
        existente.setId(1L);
        Servicio nuevo = crearServicioValido();
        nuevo.setEstado(EstadoServicio.PENDIENTE_REVISION);
        nuevo.setHorasTrabajadas(6.0);

        when(servicioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(servicioRepository.save(any(Servicio.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Servicio> resultado = servicioService.actualizar(1L, nuevo);

        assertTrue(resultado.isPresent());
        assertEquals(EstadoServicio.PENDIENTE_REVISION, resultado.get().getEstado());
        assertEquals(6.0, resultado.get().getHorasTrabajadas());
    }

    @Test
    void eliminar_idNoExistente_retornaFalse() {
        when(servicioRepository.existsById(77L)).thenReturn(false);

        assertFalse(servicioService.eliminar(77L));
    }

    @Test
    void eliminar_idExistente_retornaTrue() {
        when(servicioRepository.existsById(1L)).thenReturn(true);

        assertTrue(servicioService.eliminar(1L));
        verify(servicioRepository).deleteById(1L);
    }
}
