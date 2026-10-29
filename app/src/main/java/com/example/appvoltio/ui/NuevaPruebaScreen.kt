package com.example.appvoltio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.appvoltio.viewmodel.NuevaPruebaViewModel

@Composable
fun NuevaPruebaScreen(
    viewModel: NuevaPruebaViewModel,
    onGuardado: () -> Unit = {}
) {
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Nueva prueba",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = estado.valorMedicion,
            onValueChange = { viewModel.onValorMedicionChange(it) },
            label = { Text("Valor de medición") },
            isError = estado.errores.valorMedicion != null,
            trailingIcon = {
                if (estado.errores.valorMedicion != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error")
                }
            },
            supportingText = { estado.errores.valorMedicion?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        SelectorUnidad(
            unidadSeleccionada = estado.unidad,
            error = estado.errores.unidad,
            onUnidadSeleccionada = { viewModel.onUnidadChange(it) }
        )

        OutlinedTextField(
            value = estado.observaciones,
            onValueChange = { viewModel.onObservacionesChange(it) },
            label = { Text("Observaciones (opcional)") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "La fecha y la hora se registran automáticamente al guardar.",
            style = MaterialTheme.typography.bodySmall
        )

        Button(
            onClick = { viewModel.guardar(onGuardado) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar prueba")
        }
    }
}

private val unidades = listOf("MΩ", "kΩ", "Ω", "V", "kV", "A", "mA")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectorUnidad(
    unidadSeleccionada: String,
    error: String?,
    onUnidadSeleccionada: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = it }
    ) {
        OutlinedTextField(
            value = unidadSeleccionada,
            onValueChange = {},
            readOnly = true,
            label = { Text("Unidad") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            isError = error != null,
            supportingText = { error?.let { Text(it) } },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            unidades.forEach { unidad ->
                DropdownMenuItem(
                    text = { Text(unidad) },
                    onClick = {
                        onUnidadSeleccionada(unidad)
                        expandido = false
                    }
                )
            }
        }
    }
}