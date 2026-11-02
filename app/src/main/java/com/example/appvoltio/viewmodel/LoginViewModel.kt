package com.example.appvoltio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvoltio.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch { repository.crearAdministradorInicial() }
    }

    fun onUsuarioChange(valor: String) {
        _uiState.update {
            it.copy(usuario = valor, errores = it.errores.copy(usuario = null, general = null))
        }
    }

    fun onClaveChange(valor: String) {
        _uiState.update {
            it.copy(clave = valor, errores = it.errores.copy(clave = null, general = null))
        }
    }

    private fun validarFormato(): Boolean {
        val estado = _uiState.value
        val errores = LoginErrores(
            usuario = if (estado.usuario.isBlank()) "Campo obligatorio" else null,
            clave = if (estado.clave.isBlank()) "Campo obligatorio" else null
        )
        _uiState.update { it.copy(errores = errores) }
        return errores.usuario == null && errores.clave == null
    }

    fun ingresar(alIngresar: (Boolean) -> Unit) {
        if (!validarFormato()) return

        val estado = _uiState.value
        viewModelScope.launch {
            val usuario = repository.autenticar(estado.usuario.trim(), estado.clave)
            if (usuario == null) {
                _uiState.update {
                    it.copy(errores = it.errores.copy(general = "Usuario o contraseña incorrectos"))
                }
            } else {
                _uiState.value = LoginUiState()
                alIngresar(usuario.esAdmin)
            }
        }
    }
}