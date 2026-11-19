# Biosur Terreno — Backend de microservicios (Grupo 3)

Backend REST desarrollado en **Spring Boot 3.3.4 (Java 21)** para la asignatura **DSY1105 – Desarrollo de Aplicaciones Móviles** (Duoc UC, Experiencia 3). Provee persistencia remota y lógica de negocio para la aplicación Android [AppBiosurTerreno_Grupo3](https://github.com/FerB22/AppBiosurTerreno_Grupo3).

## Integrantes
* **Fernando Barra Muñoz**
* **Esteban Medina**

## Microservicios implementados

### 1. `ms-servicios` (puerto `8081`)
Gestión CRUD de órdenes de servicio y faenas sanitarias en terreno (`Servicio`), con base de datos H2 persistente en archivo (`./data/biosur_servicios_db`).

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/servicios` | Lista todas las faenas (admite filtros opcionales `?supervisor=` o `?estado=`). |
| `GET` | `/api/servicios/{id}` | Obtiene el detalle de una faena por su identificador. |
| `POST` | `/api/servicios` | Registra una nueva faena validando descripción, horas y costos. |
| `PUT` | `/api/servicios/{id}` | Actualiza los datos o el estado de una faena existente. |
| `DELETE` | `/api/servicios/{id}` | Elimina una faena del sistema. |

* **Swagger UI:** `http://localhost:8081/swagger-ui.html`
* **Consola H2:** `http://localhost:8081/h2-console` (JDBC URL: `jdbc:h2:file:./data/biosur_servicios_db`, usuario `sa`, sin contraseña)

### 2. `ms-usuarios` (puerto `8082`)
Gestión y autenticación corporativa de supervisores y administradores de Biosur SpA (`Usuario`), aplicando denegación por defecto (*Default Deny*) y menor privilegio (*Least Privilege*). Base de datos H2 persistente en archivo (`./data/biosur_usuarios_db`).

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/usuarios/login` | Valida dominio autorizado (`@biosur.cl` / `@administracion.biosur.cl`) y autentica/registra al usuario. |
| `GET` | `/api/usuarios` | Lista usuarios corporativos (admite filtro opcional `?rol=`). |
| `GET` | `/api/usuarios/{email}` | Consulta el perfil de un usuario por correo. |
| `POST` | `/api/usuarios` | Crea o actualiza un usuario corporativo. |
| `DELETE` | `/api/usuarios/{email}` | Elimina un usuario del registro. |

* **Swagger UI:** `http://localhost:8082/swagger-ui.html`
* **Consola H2:** `http://localhost:8082/h2-console` (JDBC URL: `jdbc:h2:file:./data/biosur_usuarios_db`, usuario `sa`, sin contraseña)

## Conexión desde Android Studio
* **Emulador Android:** utilizar `http://10.0.2.2:8081/` y `http://10.0.2.2:8082/`.
* **Teléfono físico vía USB (`adb reverse`):** ejecutar `adb reverse tcp:8081 tcp:8081` y `adb reverse tcp:8082 tcp:8082` para conectar directamente a `http://localhost:8081/` y `http://localhost:8082/`.
