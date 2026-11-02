package com.example.appvoltio.viewmodel

data class LoginUiState(
    val usuario: String = "",
    val clave: String = "",
    val errores: LoginErrores = LoginErrores()
)

data class LoginErrores(
    val usuario: String? = null,
    val clave: String? = null,
    val general: String? = null
)