package com.example.appvoltio.model

data class Prueba(
    val id: Int = 0,
    val equipoId: Int,
    val fecha: String,
    val hora: String,
    val valorMedicion: Double,
    val unidad: String,
    val observaciones: String = ""
)