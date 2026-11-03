package com.example.appvoltio.viewmodel

import com.example.appvoltio.model.Prueba

data class ComparacionPrueba(
    val anterior: Prueba,
    val actual: Prueba,
    val diferencia: Double
)