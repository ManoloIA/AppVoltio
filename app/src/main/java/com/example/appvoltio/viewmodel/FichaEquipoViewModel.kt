package com.example.appvoltio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvoltio.model.Equipo
import com.example.appvoltio.model.Prueba
import com.example.appvoltio.repository.EquipoRepository
import com.example.appvoltio.repository.PruebaRepository
import kotlin.math.round
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FichaEquipoViewModel(
    equipoId: Int,
    private val equipoRepository: EquipoRepository,
    pruebaRepository: PruebaRepository
) : ViewModel() {

    val equipo: StateFlow<Equipo?> = equipoRepository.equipoPorId(equipoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val pruebas: StateFlow<List<Prueba>> = pruebaRepository.pruebasDeEquipo(equipoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val comparacion: StateFlow<ComparacionPrueba?> = pruebas
        .map { calcularComparacion(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun eliminar(alEliminar: () -> Unit) {
        val actual = equipo.value ?: return
        viewModelScope.launch {
            equipoRepository.eliminar(actual)
            alEliminar()
        }
    }

    private fun calcularComparacion(lista: List<Prueba>): ComparacionPrueba? {
        val actual = lista.firstOrNull() ?: return null
        val anterior = lista.drop(1).firstOrNull { it.unidad == actual.unidad } ?: return null
        val diferencia = round((actual.valorMedicion - anterior.valorMedicion) * 100) / 100
        return ComparacionPrueba(anterior, actual, diferencia)
    }
}