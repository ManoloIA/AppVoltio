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

class RegistroEquipoViewModel(private val repository: EquipoRepository) : ViewModel(){

    private val _uiState = MutableStateFlow(RegistroEquipoUiState())
    val uiState: StateFlow<RegistroEquipoUiState> = _uiState.asStateFlow()

    fun onCodigoChange(valor: String){
        _uiState.update { it.copy(codigo = valor, errores = it.errores.copy(codigo = null))
        }
    }

    fun onTipoChange(valor: String){
        _uiState.update { it.copy(tipo = valor, errores = it.errores.copy(tipo = null))
        }
    }

    fun onUbicacionChange(valor: String){
        _uiState.update {it.copy(ubicacion = valor, errores = it.errores.copy(ubicacion = null))
        }
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

    fun guardar(alGuardar: () -> Unit = {}) {
        if (!validar()) return

        val estado = _uiState.value
        val equipo = Equipo(
            codigo = estado.codigo.trim(),
            tipo = estado.tipo,
            ubicacion = estado.ubicacion.trim()
        )

        viewModelScope.launch {
            repository.guardar(equipo)
            _uiState.value = RegistroEquipoUiState()
            alGuardar()
        }
    }
}