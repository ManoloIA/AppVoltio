package com.example.appvoltio.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.appvoltio.ui.components.ImagenInteligente
import com.example.appvoltio.viewmodel.RegistroEquipoViewModel
import java.io.File

@Composable
fun RegistroEquipoScreen(
    viewModel: RegistroEquipoViewModel,
    onGuardado: () -> Unit = {}
) {
    val estado by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    var uriTemporal by rememberSaveable { mutableStateOf<Uri?>(null) }

    val camaraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { fotoTomada ->
        if (fotoTomada) {
            uriTemporal?.let { viewModel.onFotoCapturada(it.toString()) }
        }
    }

    fun abrirCamara() {
        val uri = crearUriFoto(context)
        uriTemporal = uri
        camaraLauncher.launch(uri)
    }

    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) abrirCamara() else viewModel.onPermisoCamaraDenegado()
    }

    fun registrarUbicacion() {
        obtenerUbicacion(context) { ubicacion ->
            if (ubicacion != null) {
                viewModel.onUbicacionGpsObtenida(ubicacion.latitude, ubicacion.longitude)
            } else {
                viewModel.onUbicacionGpsNoDisponible()
            }
        }
    }

    val permisoUbicacionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) registrarUbicacion() else viewModel.onPermisoUbicacionDenegado()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Registrar equipo",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = estado.codigo,
            onValueChange = { viewModel.onCodigoChange(it) },
            label = { Text("Código del equipo") },
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
            onTipoSeleccionado = { viewModel.onTipoChange(it) }
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

        OutlinedButton(
            onClick = {
                val permisoConcedido = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                if (permisoConcedido) {
                    registrarUbicacion()
                } else {
                    permisoUbicacionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (estado.latitud == null) "Registrar ubicación GPS" else "Actualizar ubicación GPS")
        }

        AnimatedVisibility(
            visible = estado.latitud != null && estado.longitud != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Ubicación GPS registrada: %.5f, %.5f".format(estado.latitud, estado.longitud),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        AnimatedVisibility(
            visible = estado.errores.gps != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Warning,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = estado.errores.gps ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Text(
            text = "Foto de la placa (opcional)",
            style = MaterialTheme.typography.titleSmall
        )

        ImagenInteligente(
            fotoUri = estado.fotoUri,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        OutlinedButton(
            onClick = {
                val permisoConcedido = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
                if (permisoConcedido) {
                    abrirCamara()
                } else {
                    permisoLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (estado.fotoUri == null) "Tomar foto" else "Volver a tomar foto")
        }

        AnimatedVisibility(
            visible = estado.errores.foto != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Warning,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = estado.errores.foto ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

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
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = it }
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
            onDismissRequest = { expandido = false }
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

private fun crearUriFoto(context: Context): Uri {
    val carpeta = File(context.filesDir, "fotos").apply { mkdirs() }
    val archivo = File(carpeta, "placa_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", archivo)
}

@SuppressLint("MissingPermission")
private fun obtenerUbicacion(context: Context, onResultado: (Location?) -> Unit) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    locationManager.getCurrentLocation(
        LocationManager.GPS_PROVIDER,
        null,
        ContextCompat.getMainExecutor(context)
    ) { ubicacion -> onResultado(ubicacion) }
}