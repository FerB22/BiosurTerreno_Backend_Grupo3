# Biosur Terreno — Backend de microservicios (Grupo 3)

Backend REST desarrollado en **Spring Boot (Java 21)** para la asignatura **DSY1105 – Desarrollo de Aplicaciones Móviles** (Duoc UC, Experiencia 3). Provee persistencia remota y servicios de dominio para la aplicación Android [AppBiosurTerreno_Grupo3](https://github.com/FerB22/AppBiosurTerreno_Grupo3).

## Integrantes
* **Fernando Barra Muñoz**
* **Esteban Medina**

## Estructura planificada de microservicios
* `ms-servicios/` (puerto `8081`): Gestión CRUD completa de órdenes de servicio y faenas sanitarias en terreno (`Servicio`), con base de datos activa.
* `ms-usuarios/` (puerto `8082`): Gestión de supervisores, autenticación corporativa por roles (`RolUsuario`) y validación de cuentas autorizadas.
