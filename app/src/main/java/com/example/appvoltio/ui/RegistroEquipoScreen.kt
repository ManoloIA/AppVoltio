package com.example.appvoltio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun RegistroEquipoScreen(viewModel: RegistroEquipoViewModel){
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
            supportingText = { estado.errores.codigo?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.ubicacion,
            onValueChange = { viewModel.onUbicacionChange(it) },
            label = { Text("Ubicación en la instalación del cliente") },
            isError = estado.errores.ubicacion != null,
            supportingText = { estado.errores.ubicacion?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.guardar() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar equipo")
        }
    }
}