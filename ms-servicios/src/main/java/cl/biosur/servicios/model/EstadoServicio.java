package cl.biosur.servicios.model;

/**
 * Estados del flujo operativo de una orden de servicio en terreno.
 */
public enum EstadoServicio {
    EN_PROCESO("En faena"),
    FINALIZADO("Finalizado"),
    PENDIENTE_REVISION("Pendiente de informe");

    private final String etiqueta;

    EstadoServicio(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
