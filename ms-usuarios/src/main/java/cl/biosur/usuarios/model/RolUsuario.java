package cl.biosur.usuarios.model;

/**
 * Roles operativos autorizados dentro de Biosur Terreno según principio de menor privilegio.
 */
public enum RolUsuario {
    SUPERVISOR_TERRENO("Supervisor de terreno", "biosur.cl"),
    ADMINISTRADOR("Administración central", "administracion.biosur.cl");

    private final String titulo;
    private final String dominioAutorizado;

    RolUsuario(String titulo, String dominioAutorizado) {
        this.titulo = titulo;
        this.dominioAutorizado = dominioAutorizado;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDominioAutorizado() {
        return dominioAutorizado;
    }
}
