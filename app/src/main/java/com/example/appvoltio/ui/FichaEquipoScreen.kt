package com.example.appvoltio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appvoltio.model.Prueba
import com.example.appvoltio.ui.components.ImagenInteligente
import com.example.appvoltio.viewmodel.FichaEquipoViewModel

@Composable
fun FichaEquipoScreen(
    viewModel: FichaEquipoViewModel,
    onNuevaPrueba: () -> Unit
) {
    val equipo by viewModel.equipo.collectAsState()
    val pruebas by viewModel.pruebas.collectAsState()
    val comparacion by viewModel.comparacion.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Ficha del equipo",
            style = MaterialTheme.typography.headlineSmall
        )

        equipo?.let { e ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ImagenInteligente(
                        fotoUri = e.fotoUri,
                        modifier = Modifier.size(96.dp)
                    )
                    Column {
                        Text(
                            text = e.codigo,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = e.tipo)
                        Text(
                            text = e.ubicacion,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (e.latitud != null && e.longitud != null) {
                            Text(
                                text = "GPS: ${e.latitud}, ${e.longitud}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        comparacion?.let { c ->
            val signo = if (c.diferencia > 0) "+" else ""
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Comparación con la prueba anterior",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Anterior (${c.anterior.fecha}): ${c.anterior.valorMedicion} ${c.anterior.unidad}"
                    )
                    Text(
                        text = "Actual (${c.actual.fecha}): ${c.actual.valorMedicion} ${c.actual.unidad}"
                    )
                    Text(
                        text = "Diferencia: $signo${c.diferencia} ${c.actual.unidad}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Text(
            text = "Historial de pruebas",
            style = MaterialTheme.typography.titleMedium
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = if (pruebas.isEmpty()) Alignment.Center else Alignment.TopStart
        ) {
            if (pruebas.isEmpty()) {
                Text(
                    text = "Este equipo aún no tiene pruebas registradas",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(pruebas, key = { it.id }) { prueba ->
                        TarjetaPrueba(
                            prueba = prueba,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem()
                        )
                    }
                }
            }
        }

        Button(
            onClick = onNuevaPrueba,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva prueba")
        }
    }
}

@Composable
private fun TarjetaPrueba(
    prueba: Prueba,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${prueba.fecha} · ${prueba.hora}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = prueba.observaciones.ifBlank { "Sin observaciones" },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "${prueba.valorMedicion} ${prueba.unidad}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}