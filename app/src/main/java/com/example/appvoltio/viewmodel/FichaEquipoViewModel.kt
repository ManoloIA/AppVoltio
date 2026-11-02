package com.example.appvoltio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvoltio.model.Equipo
import com.example.appvoltio.model.Prueba
import com.example.appvoltio.repository.EquipoRepository
import com.example.appvoltio.repository.PruebaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FichaEquipoViewModel(
    equipoId: Int,
    equipoRepository: EquipoRepository,
    pruebaRepository: PruebaRepository
) : ViewModel() {

    val equipo: StateFlow<Equipo?> = equipoRepository.equipoPorId(equipoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val pruebas: StateFlow<List<Prueba>> = pruebaRepository.pruebasDeEquipo(equipoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}