package com.example.appvoltio.model

data class Equipo(
    val id: Int = 0,
    val codigo: String,
    val tipo: String,
    val ubicacion: String,
    val fotoUri: String? = null
)