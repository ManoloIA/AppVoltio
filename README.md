# AppVoltio

App móvil Android para digitalizar el protocolo de pruebas eléctricas en terreno
(caso Induztek Ingeniería SpA). Proyecto de la Evaluación Parcial 2 de DSY1105
Desarrollo de Aplicaciones Móviles, Duoc UC.

## Integrantes

* Manuel Roblero
* Cristofer Antinao

## Tecnologías

Kotlin, Jetpack Compose, Material Design 3, arquitectura MVVM, Room (SQLite),
Navigation Compose y Coil.

## Funcionalidades implementadas

* Inicio de sesión con validación por campo y mensaje de credenciales incorrectas.
* Usuario administrador creado automáticamente al iniciar la app por primera vez.
* Creación de usuarios técnicos (solo administrador), con validación por campo,
  confirmación de contraseña y control de usuario duplicado.
* Pantalla de inicio según el rol: solo el administrador ve la opción Crear usuario.
* Registro de equipos con validación por campo (ícono y mensaje de error).
* Foto de la placa del equipo con la cámara del dispositivo.
* Ubicación del equipo con el GPS del dispositivo.
* Lista de equipos con búsqueda por código o ubicación.
* Ficha del equipo con sus datos, foto, ubicación GPS, historial de pruebas y comparación con la prueba anterior.
* Registro de pruebas eléctricas con fecha, hora, valor de medición, unidad y observaciones.
* Animaciones en mensajes de error, mensajes de éxito, listas e ícono animado en el inicio de sesión.
* Tema Material 3 con modo claro y oscuro.

## Recursos nativos

* Cámara: foto de la placa del equipo (permiso en tiempo de ejecución y FileProvider).
* GPS: ubicación del equipo al registrarlo (permiso en tiempo de ejecución).

## Arquitectura

Flujo MVVM: Pantalla → ViewModel → Repository → DAO → Room.

* `model`: entidades de Room (Equipo, Prueba, Usuario).
* `data`: DAOs y base de datos.
* `repository`: acceso a los datos.
* `viewmodel`: estado de cada pantalla, validaciones y factories.
* `ui`: pantallas en Jetpack Compose, componentes, navegación y tema.

## Usuario de prueba

* Usuario: `admin`
* Contraseña: `admin123`

## Cómo ejecutar

1. Clonar el repositorio.
2. Abrir la carpeta en Android Studio y esperar la sincronización de Gradle.
3. Ejecutar en un emulador o dispositivo con Android 13 (API 33) o superior.
4. Si había una versión anterior instalada, desinstalarla antes de ejecutar.

## Gestión del proyecto

* GitHub: una rama `feature/` por tarea y Pull Request revisado por el otro integrante.
* Trello: https://trello.com/b/SV6vSGmW/appvoltio