package com.example.appvoltio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appvoltio.viewmodel.ListaEquiposViewModel

@Composable
fun ListaEquiposScreen(
    viewModel: ListaEquiposViewModel,
    onEquipoClick: (Int) -> Unit,
    onRegistrarClick: () -> Unit
) {
    val estado by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargar()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Equipos",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = estado.busqueda,
            onValueChange = { viewModel.onBusquedaChange(it) },
            label = { Text("Buscar por código o ubicación") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.weight(1f)) {
            if (estado.equipos.isEmpty()) {
                Text(
                    text = if (estado.busqueda.isBlank()) {
                        "Aún no hay equipos registrados"
                    } else {
                        "Ningún equipo coincide con la búsqueda"
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(estado.equipos) { equipo ->
                        Card(
                            onClick = { onEquipoClick(equipo.id) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = equipo.codigo,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = equipo.tipo)
                                Text(
                                    text = equipo.ubicacion,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = onRegistrarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar equipo")
        }
    }
}