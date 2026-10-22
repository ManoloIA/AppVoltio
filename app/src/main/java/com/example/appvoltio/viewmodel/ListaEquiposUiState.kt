package com.example.appvoltio.viewmodel

import com.example.appvoltio.model.Equipo

data class ListaEquiposUiState(
    val equipos: List<Equipo> = emptyList(),
    val busqueda: String = ""
)