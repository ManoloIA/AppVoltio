package com.example.appvoltio.viewmodel


data class NuevaPruebaUiState(
    val valorMedicion: String = "",
    val unidad: String = "",
    val observaciones: String ="",
    val errores: NuevaPruebaErrores = NuevaPruebaErrores()
)

data class NuevaPruebaErrores(
    val valorMedicion: String? = null,
    val unidad: String? = null,
)
