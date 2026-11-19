package cl.biosur.usuarios.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Objeto de transferencia para solicitudes de autenticación desde la app móvil.
 */
public class LoginRequest {

    @NotBlank(message = "El correo electrónico es obligatorio")
    private String email;

    private String nombre;

    public LoginRequest() {
    }

    public LoginRequest(String email, String nombre) {
        this.email = email;
        this.nombre = nombre;
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
}
