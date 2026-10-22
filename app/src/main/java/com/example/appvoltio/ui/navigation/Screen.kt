package com.example.appvoltio.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Inicio : Screen("inicio")
    object CrearUsuario : Screen("crear_usuario")
    object RegistrarEquipo : Screen("registrar_equipo")

    // seleccionar = true cuando se viene de "Nueva prueba" para elegir el equipo
    object ListaEquipos : Screen("lista_equipos/{seleccionar}") {
        fun createRoute(seleccionar: Boolean) = "lista_equipos/$seleccionar"
    }

    object FichaEquipo : Screen("ficha_equipo/{equipoId}") {
        fun createRoute(equipoId: Int) = "ficha_equipo/$equipoId"
    }

    object NuevaPrueba : Screen("nueva_prueba/{equipoId}") {
        fun createRoute(equipoId: Int) = "nueva_prueba/$equipoId"
    }

    object Resumen : Screen("resumen/{pruebaId}") {
        fun createRoute(pruebaId: Int) = "resumen/$pruebaId"
    }
}