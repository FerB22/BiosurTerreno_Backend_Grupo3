package cl.biosur.servicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad JPA que representa una faena u orden de servicio sanitario ejecutada en terreno.
 */
@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La fecha es obligatoria")
    @Column(nullable = false, length = 20)
    private String fecha;

    @NotBlank(message = "La hora de inicio es obligatoria")
    @Column(nullable = false, length = 10)
    private String horaInicio;

    @NotBlank(message = "La hora de término es obligatoria")
    @Column(nullable = false, length = 10)
    private String horaTermino;

    @NotBlank(message = "El supervisor responsable es obligatorio")
    @Column(nullable = false, length = 100)
    private String supervisor;

    @NotNull(message = "El tipo de servicio es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoServicio tipoServicio;

    @NotBlank(message = "La descripción técnica no puede estar vacía")
    @Size(min = 10, message = "La descripción debe detallar al menos 10 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @DecimalMin(value = "0.1", message = "Las horas trabajadas deben ser mayores que 0")
    @DecimalMax(value = "24.0", message = "Las horas del turno no pueden superar las 24 horas")
    @Column(nullable = false)
    private double horasTrabajadas;

    @DecimalMin(value = "0.0", message = "El costo estimado no puede ser negativo")
    @Column(nullable = false)
    private double costoEstimado;

    private Double latitud;
    private Double longitud;

    @Column(length = 500)
    private String fotoAntesUri;

    @Column(length = 500)
    private String fotoDespuesUri;

    @NotNull(message = "El estado operativo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoServicio estado = EstadoServicio.FINALIZADO;

    public Servicio() {
    }

    public Servicio(String fecha, String horaInicio, String horaTermino, String supervisor,
                    TipoServicio tipoServicio, String descripcion, double horasTrabajadas,
                    double costoEstimado, Double latitud, Double longitud,
                    String fotoAntesUri, String fotoDespuesUri, EstadoServicio estado) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.supervisor = supervisor;
        this.tipoServicio = tipoServicio;
        this.descripcion = descripcion;
        this.horasTrabajadas = horasTrabajadas;
        this.costoEstimado = costoEstimado;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fotoAntesUri = fotoAntesUri;
        this.fotoDespuesUri = fotoDespuesUri;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(String horaTermino) {
        this.horaTermino = horaTermino;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getFotoAntesUri() {
        return fotoAntesUri;
    }

    public void setFotoAntesUri(String fotoAntesUri) {
        this.fotoAntesUri = fotoAntesUri;
    }

    public String getFotoDespuesUri() {
        return fotoDespuesUri;
    }

    public void setFotoDespuesUri(String fotoDespuesUri) {
        this.fotoDespuesUri = fotoDespuesUri;
    }

    public EstadoServicio getEstado() {
        return estado;
    }

    public void setEstado(EstadoServicio estado) {
        this.estado = estado;
    }
}
