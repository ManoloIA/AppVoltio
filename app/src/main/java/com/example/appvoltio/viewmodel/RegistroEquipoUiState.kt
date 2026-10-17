package com.example.appvoltio.viewmodel

data class RegistroEquipoErrores(
    val codigo: String? = null,
    val tipo: String? = null,
    val ubicacion: String? = null
)

data class RegistroEquipoUiState(
    val codigo: String = "",
    val tipo: String = "",
    val ubicacion: String = "",
    val errores: RegistroEquipoErrores = RegistroEquipoErrores()

)