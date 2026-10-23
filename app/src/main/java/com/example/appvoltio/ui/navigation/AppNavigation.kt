package com.example.appvoltio.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appvoltio.ui.RegistroEquipoScreen
import com.example.appvoltio.ui.components.PantallaTemporal
import com.example.appvoltio.viewmodel.RegistroEquipoViewModel
import com.example.appvoltio.ui.ListaEquiposScreen
import com.example.appvoltio.viewmodel.ListaEquiposViewModel

@Composable
fun AppNavigation(
    registroEquipoViewModel: RegistroEquipoViewModel,
    listaEquiposViewModel: ListaEquiposViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            PantallaTemporal(
                titulo = "Login",
                acciones = listOf(
                    "Ingresar" to {
                        navController.navigate(Screen.Inicio.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            )
        }

        composable(Screen.Inicio.route) {
            PantallaTemporal(
                titulo = "Inicio",
                acciones = listOf(
                    "Buscar un equipo" to {
                        navController.navigate(Screen.ListaEquipos.createRoute(false))
                    },
                    "Registrar un equipo" to {
                        navController.navigate(Screen.RegistrarEquipo.route)
                    },
                    "Nueva prueba" to {
                        navController.navigate(Screen.ListaEquipos.createRoute(true))
                    },
                    "Crear usuario" to {
                        navController.navigate(Screen.CrearUsuario.route)
                    },
                    "Cerrar sesión" to {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Inicio.route) { inclusive = true }
                        }
                    }
                )
            )
        }

        composable(Screen.CrearUsuario.route) {
            PantallaTemporal(
                titulo = "Crear usuario",
                acciones = listOf("Volver" to { navController.popBackStack() })
            )
        }

        composable(Screen.RegistrarEquipo.route) {
            RegistroEquipoScreen(registroEquipoViewModel)
        }

        composable(
            route = Screen.ListaEquipos.route,
            arguments = listOf(navArgument("seleccionar") { type = NavType.BoolType })
        ) { backStackEntry ->
            val seleccionar = backStackEntry.arguments?.getBoolean("seleccionar") ?: false
            ListaEquiposScreen(
                viewModel = listaEquiposViewModel,
                onEquipoClick = { equipoId ->
                    if (seleccionar) {
                        navController.navigate(Screen.NuevaPrueba.createRoute(equipoId))
                    } else {
                        navController.navigate(Screen.FichaEquipo.createRoute(equipoId))
                    }
                },
                onRegistrarClick = {
                    navController.navigate(Screen.RegistrarEquipo.route)
                }
            )
        }

        composable(
            route = Screen.FichaEquipo.route,
            arguments = listOf(navArgument("equipoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getInt("equipoId") ?: 0
            PantallaTemporal(
                titulo = "Ficha del equipo $equipoId",
                acciones = listOf(
                    "Nueva prueba" to {
                        navController.navigate(Screen.NuevaPrueba.createRoute(equipoId))
                    },
                    "Volver" to { navController.popBackStack() }
                )
            )
        }

        composable(
            route = Screen.NuevaPrueba.route,
            arguments = listOf(navArgument("equipoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getInt("equipoId") ?: 0
            PantallaTemporal(
                titulo = "Nueva prueba (equipo $equipoId)",
                acciones = listOf(
                    "Guardar prueba" to {
                        navController.navigate(Screen.Resumen.createRoute(1))
                    },
                    "Volver" to { navController.popBackStack() }
                )
            )
        }

        composable(
            route = Screen.Resumen.route,
            arguments = listOf(navArgument("pruebaId") { type = NavType.IntType })
        ) {
            PantallaTemporal(
                titulo = "Resumen",
                acciones = listOf(
                    "Volver a la ficha" to {
                        navController.popBackStack(Screen.FichaEquipo.route, false)
                    },
                    "Ir a la lista de equipos" to {
                        navController.navigate(Screen.ListaEquipos.createRoute(false)) {
                            popUpTo(Screen.Inicio.route)
                        }
                    }
                )
            )
        }
    }
}