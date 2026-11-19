package cl.biosur.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad JPA representativa de un usuario corporativo de Biosur SpA.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Email(message = "El formato del correo electrónico no es válido")
    @NotBlank(message = "El correo corporativo es obligatorio")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RolUsuario rol;

    @Column(nullable = false)
    private boolean activo = true;

    public Usuario() {
    }

    public Usuario(String email, String nombre, RolUsuario rol, boolean activo) {
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
        this.activo = activo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
