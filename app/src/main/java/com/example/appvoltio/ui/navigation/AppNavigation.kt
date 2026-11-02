package com.example.appvoltio.ui.navigation

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appvoltio.ui.NuevaPruebaScreen
import com.example.appvoltio.viewmodel.NuevaPruebaViewModel
import com.example.appvoltio.viewmodel.NuevaPruebaViewModelFactory
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
import com.example.appvoltio.ui.LoginScreen
import com.example.appvoltio.viewmodel.LoginViewModel
import com.example.appvoltio.viewmodel.LoginViewModelFactory
import com.example.appvoltio.ui.CrearUsuarioScreen
import com.example.appvoltio.viewmodel.CrearUsuarioViewModel
import com.example.appvoltio.viewmodel.CrearUsuarioViewModelFactory
import com.example.appvoltio.ui.FichaEquipoScreen
import com.example.appvoltio.viewmodel.FichaEquipoViewModel
import com.example.appvoltio.viewmodel.FichaEquipoViewModelFactory

@Composable
fun AppNavigation(
    registroEquipoViewModel: RegistroEquipoViewModel,
    listaEquiposViewModel: ListaEquiposViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            val app = LocalContext.current.applicationContext as Application
            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(app))
            LoginScreen(
                viewModel = loginViewModel,
                onIngresoExitoso = { esAdmin ->
                    navController.navigate(Screen.Inicio.createRoute(esAdmin)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Inicio.route,
            arguments = listOf(navArgument("esAdmin") { type = NavType.BoolType })
        ) {
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
            val app = LocalContext.current.applicationContext as Application
            val crearUsuarioViewModel: CrearUsuarioViewModel = viewModel(
                factory = CrearUsuarioViewModelFactory(app)
            )
            CrearUsuarioScreen(
                viewModel = crearUsuarioViewModel,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Screen.RegistrarEquipo.route) {
            RegistroEquipoScreen(
                viewModel = registroEquipoViewModel,
                onGuardado = { navController.popBackStack() }
            )
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
            val app = LocalContext.current.applicationContext as Application
            val fichaEquipoViewModel: FichaEquipoViewModel = viewModel(
                factory = FichaEquipoViewModelFactory(app, equipoId)
            )
            FichaEquipoScreen(
                viewModel = fichaEquipoViewModel,
                onNuevaPrueba = {
                    navController.navigate(Screen.NuevaPrueba.createRoute(equipoId))
                }
            )
        }

        composable(
            route = Screen.NuevaPrueba.route,
            arguments = listOf(navArgument("equipoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getInt("equipoId") ?: 0
            val app = LocalContext.current.applicationContext as Application
            val nuevaPruebaViewModel: NuevaPruebaViewModel = viewModel(
                factory = NuevaPruebaViewModelFactory(app, equipoId)
            )
            NuevaPruebaScreen(
                viewModel = nuevaPruebaViewModel,
                onGuardado = { navController.popBackStack()}
            )
        }
    }
}