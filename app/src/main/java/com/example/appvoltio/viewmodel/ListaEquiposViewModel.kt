package com.example.appvoltio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvoltio.model.Equipo
import com.example.appvoltio.repository.EquipoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListaEquiposViewModel(private val repository: EquipoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaEquiposUiState())
    val uiState: StateFlow<ListaEquiposUiState> = _uiState.asStateFlow()

    // Todos los equipos guardados, sin filtrar
    private var todos: List<Equipo> = emptyList()

    fun cargar() {
        viewModelScope.launch {
            todos = repository.obtenerTodos()
            aplicarFiltro(_uiState.value.busqueda)
        }
    }

    fun onBusquedaChange(texto: String) {
        aplicarFiltro(texto)
    }

    private fun aplicarFiltro(texto: String) {
        val consulta = texto.trim()
        val filtrados = if (consulta.isEmpty()) {
            todos
        } else {
            todos.filter {
                it.codigo.contains(consulta, ignoreCase = true) ||
                        it.ubicacion.contains(consulta, ignoreCase = true)
            }
        }
        _uiState.update { it.copy(busqueda = texto, equipos = filtrados) }
    }
}