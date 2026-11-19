package cl.biosur.servicios;

import cl.biosur.servicios.model.EstadoServicio;
import cl.biosur.servicios.model.Servicio;
import cl.biosur.servicios.model.TipoServicio;
import cl.biosur.servicios.repository.ServicioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MsServiciosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsServiciosApplication.class, args);
    }

    @Bean
    CommandLineRunner cargarServiciosIniciales(ServicioRepository servicioRepository) {
        return args -> {
            if (servicioRepository.count() == 0) {
                servicioRepository.saveAll(List.of(
                        new Servicio(
                                "18/11/2026", "08:30", "12:30",
                                "Fernando Barra Muñoz",
                                TipoServicio.FOSA_SEPTICA,
                                "Succión y limpieza integral de fosa séptica en sector industrial Nacimiento.",
                                4.0, 140000.0,
                                -37.5021, -72.6732,
                                null, null,
                                EstadoServicio.FINALIZADO
                        ),
                        new Servicio(
                                "19/11/2026", "09:00", "14:00",
                                "Esteban Medina",
                                TipoServicio.PLANTA_ELEVADORA,
                                "Inspección electromecánica de bombas y tablero en planta elevadora sector centro.",
                                5.0, 210000.0,
                                -36.8270, -73.0503,
                                null, null,
                                EstadoServicio.EN_PROCESO
                        )
                ));
            }
        };
    }
}
