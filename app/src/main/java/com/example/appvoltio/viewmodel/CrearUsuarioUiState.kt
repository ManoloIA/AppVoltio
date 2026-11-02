package com.example.appvoltio.viewmodel

data class CrearUsuarioUiState(
    val usuario: String = "",
    val clave: String = "",
    val confirmarClave: String = "",
    val usuarioCreado: String? = null,
    val errores: CrearUsuarioErrores = CrearUsuarioErrores()
)

data class CrearUsuarioErrores(
    val usuario: String? = null,
    val clave: String? = null,
    val confirmarClave: String? = null
)