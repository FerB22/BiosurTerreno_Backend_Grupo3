package cl.biosur.servicios.model;

/**
 * Tipos de servicios sanitarios operados por Biosur SpA.
 */
public enum TipoServicio {
    FOSA_SEPTICA("Limpieza de fosa séptica"),
    PLANTA_ELEVADORA("Mantenimiento de planta elevadora"),
    OBRA_CIVIL("Obra civil sanitaria"),
    EMERGENCIA("Atención de emergencia");

    private final String etiqueta;

    TipoServicio(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
