package com.example.appvoltio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InicioScreen(
    esAdmin: Boolean,
    onBuscarEquipo: () -> Unit,
    onRegistrarEquipo: () -> Unit,
    onNuevaPrueba: () -> Unit,
    onCrearUsuario: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "AppVoltio",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = if (esAdmin) "Sesión de administrador" else "Sesión de técnico",
            style = MaterialTheme.typography.bodyMedium
        )

        TarjetaAccion(
            titulo = "Buscar un equipo",
            descripcion = "Consulta la lista de equipos y su historial de pruebas",
            onClick = onBuscarEquipo
        )
        TarjetaAccion(
            titulo = "Registrar un equipo",
            descripcion = "Agrega un equipo con su foto y ubicación",
            onClick = onRegistrarEquipo
        )
        TarjetaAccion(
            titulo = "Nueva prueba",
            descripcion = "Elige el equipo y registra la medición",
            onClick = onNuevaPrueba
        )

        if (esAdmin) {
            TarjetaAccion(
                titulo = "Crear usuario",
                descripcion = "Crea la cuenta de un técnico de terreno",
                onClick = onCrearUsuario
            )
        }

        OutlinedButton(
            onClick = onCerrarSesion,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
private fun TarjetaAccion(
    titulo: String,
    descripcion: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}