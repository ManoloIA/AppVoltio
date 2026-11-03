package com.example.appvoltio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appvoltio.ui.components.CampoClave
import com.example.appvoltio.viewmodel.LoginViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.size
import com.example.appvoltio.ui.components.IconoRayoAnimado

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onIngresoExitoso: (Boolean) -> Unit
) {
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        IconoRayoAnimado(modifier = Modifier.size(72.dp))
        Text(
            text = "AppVoltio",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Inicia sesión para continuar",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = estado.usuario,
            onValueChange = { viewModel.onUsuarioChange(it) },
            label = { Text("Usuario") },
            isError = estado.errores.usuario != null,
            trailingIcon = {
                if (estado.errores.usuario != null) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error")
                }
            },
            supportingText = { estado.errores.usuario?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        CampoClave(
            valor = estado.clave,
            onValorChange = { viewModel.onClaveChange(it) },
            etiqueta = "Contraseña",
            error = estado.errores.clave
        )

        AnimatedVisibility(visible = estado.errores.general != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Warning,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = estado.errores.general ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Button(
            onClick = { viewModel.ingresar(onIngresoExitoso) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}