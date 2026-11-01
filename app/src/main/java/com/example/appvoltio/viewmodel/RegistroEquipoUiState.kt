package com.example.appvoltio.viewmodel

data class RegistroEquipoErrores(
    val codigo: String? = null,
    val tipo: String? = null,
    val ubicacion: String? = null,
    val foto: String? = null,
    val gps: String? = null
)

data class RegistroEquipoUiState(
    val codigo: String = "",
    val tipo: String = "",
    val ubicacion: String = "",
    val fotoUri: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null,
    val errores: RegistroEquipoErrores = RegistroEquipoErrores()
)