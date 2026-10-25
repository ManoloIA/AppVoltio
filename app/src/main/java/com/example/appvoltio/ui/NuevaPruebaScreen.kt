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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.appvoltio.viewmodel.NuevaPruebaViewModel

@Composable
fun NuevaPruebaScreen(
    viewModel: NuevaPruebaViewModel,
    onGuardado: () -> Unit = {}
){
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
            supportingText = {estado.errores.valorMedicion?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.unidad,
            onValueChange = { viewModel.onUnidadChange(it) },
            label = { Text("Unidad (por ejemplo MΩ, V, A)") },
            isError = estado.errores.unidad != null,
            trailingIcon = {
                if (estado.errores.unidad != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error")
                }
            },
            supportingText = { estado.errores.unidad?.let { Text(it)} },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.observaciones,
            onValueChange = { viewModel.onObservacionesChange(it) },
            label = { Text("Observaciones (opcional)") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "la fecha y la hora se registran automáticamente al guardar.",
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