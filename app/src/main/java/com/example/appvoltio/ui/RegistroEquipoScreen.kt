package com.example.appvoltio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appvoltio.viewmodel.RegistroEquipoViewModel
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun RegistroEquipoScreen(
    viewModel: RegistroEquipoViewModel,
    onGuardado: () -> Unit ={}
) {
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ){
        Text(
            text = "Registrar equipo",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = estado.codigo,
            onValueChange = {viewModel.onCodigoChange(it) },
            label = {Text("Código del equipo") },
            isError = estado.errores.codigo != null,
            trailingIcon = {
                if (estado.errores.codigo != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error")
                }
            },
            supportingText = { estado.errores.codigo?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        SelectorTipo(
            tipoSeleccionado = estado.tipo,
            error = estado.errores.tipo,
            onTipoSeleccionado = { viewModel.onTipoChange(it)}
        )

        OutlinedTextField(
            value = estado.ubicacion,
            onValueChange = { viewModel.onUbicacionChange(it) },
            label = { Text("Ubicación en la instalación del cliente") },
            isError = estado.errores.ubicacion != null,
            trailingIcon = {
                if (estado.errores.ubicacion != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error")
                }
            },
            supportingText = { estado.errores.ubicacion?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.guardar(onGuardado) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar equipo")
        }
    }
}

private val tiposDeEquipo = listOf("Transformador", "Relé de protección", "Celda", "Cable")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectorTipo(
    tipoSeleccionado: String,
    error: String?,
    onTipoSeleccionado: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false)}

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = it}
    ) {
        OutlinedTextField(
            value = tipoSeleccionado,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de equipo") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            isError = error != null,
            supportingText = { error?.let { Text(it) } },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false}
        ) {
            tiposDeEquipo.forEach { tipo ->
                DropdownMenuItem(
                    text = { Text(tipo) },
                    onClick = {
                        onTipoSeleccionado(tipo)
                        expandido = false
                    }
                )
            }
        }

    }
}