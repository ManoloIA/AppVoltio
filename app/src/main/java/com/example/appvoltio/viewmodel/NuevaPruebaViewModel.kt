package com.example.appvoltio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvoltio.model.Prueba
import com.example.appvoltio.repository.PruebaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class NuevaPruebaViewModel(
    private val equipoId: Int,
    private val repository: PruebaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NuevaPruebaUiState())
    val uiState: StateFlow<NuevaPruebaUiState> = _uiState.asStateFlow()

    fun onValorMedicionChange(valor: String) {
        _uiState.update {
            it.copy(valorMedicion = valor, errores = it.errores.copy(valorMedicion = null))
        }
    }

    fun onUnidadChange(valor: String) {
        _uiState.update {
            it.copy(unidad = valor, errores = it.errores.copy(unidad = null))
        }
    }

    fun onObservacionesChange(valor: String) {
        _uiState.update { it.copy(observaciones = valor) }
    }

    private fun validar(): Boolean {
        val estado = _uiState.value

        val errorValor = when {
            estado.valorMedicion.isBlank() -> "Campo obligatorio"
            estado.valorMedicion.replace(",", ".")
                .toDoubleOrNull() == null -> "Ingrese un número válido"

            else -> null
        }

        val errores = NuevaPruebaErrores(
            valorMedicion = errorValor,
            unidad = if (estado.unidad.isBlank()) "Campo obligatorio" else null
        )
        _uiState.update { it.copy(errores = errores) }

        return errores.valorMedicion == null && errores.unidad == null
    }

    fun guardar(alGuardar: () -> Unit = {}) {
        if (!validar()) return

        val estado = _uiState.value
        val prueba = Prueba(
            equipoId = equipoId,
            fecha = LocalDate.now().toString(),
            hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
            valorMedicion = estado.valorMedicion.replace(",", ".").toDouble(),
            unidad = estado.unidad.trim(),
            observaciones = estado.observaciones.trim()
        )

        viewModelScope.launch {
            repository.guardar(prueba)
            _uiState.value = NuevaPruebaUiState()
            alGuardar()
        }
    }
}