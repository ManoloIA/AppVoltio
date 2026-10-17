package com.example.appvoltio.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistroEquipoViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(RegistroEquipoUiState())
    val uiState: StateFlow<RegistroEquipoUiState> = _uiState.asStateFlow()

    fun onCodigoChange(valor: String){
        _uiState.update { it.copy(codigo = valor)}
    }

    fun onTipoChange(valor: String){
        _uiState.update { it.copy(tipo = valor)}
    }

    fun onUbicacionChange(valor: String){
        _uiState.update {it.copy(ubicacion = valor)}
    }
}