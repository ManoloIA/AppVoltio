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

    private fun validar(): Boolean {
        val estado = _uiState.value

        val errores = RegistroEquipoErrores(
            codigo = if (estado.codigo.isBlank()) "Campo obligatorio" else null,
            tipo = if (estado.tipo.isBlank()) "Campo obligatorio" else  null,
            ubicacion = if (estado.ubicacion.isBlank()) "Campo obligatorio" else null
        )

        _uiState.update {it.copy(errores = errores) }

        return errores.codigo == null && errores.tipo == null && errores.ubicacion == null
    }
}