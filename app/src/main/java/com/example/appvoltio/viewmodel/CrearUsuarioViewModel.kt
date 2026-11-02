package com.example.appvoltio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvoltio.model.Usuario
import com.example.appvoltio.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrearUsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearUsuarioUiState())
    val uiState: StateFlow<CrearUsuarioUiState> = _uiState.asStateFlow()

    fun onUsuarioChange(valor: String) {
        _uiState.update {
            it.copy(
                usuario = valor,
                usuarioCreado = null,
                errores = it.errores.copy(usuario = null)
            )
        }
    }

    fun onClaveChange(valor: String) {
        _uiState.update {
            it.copy(
                clave = valor,
                usuarioCreado = null,
                errores = it.errores.copy(clave = null, confirmarClave = null)
            )
        }
    }

    fun onConfirmarClaveChange(valor: String) {
        _uiState.update {
            it.copy(
                confirmarClave = valor,
                usuarioCreado = null,
                errores = it.errores.copy(confirmarClave = null)
            )
        }
    }

    private fun validarFormato(): Boolean {
        val estado = _uiState.value

        val errores = CrearUsuarioErrores(
            usuario = when {
                estado.usuario.isBlank() -> "Campo obligatorio"
                estado.usuario.trim().length < 3 -> "Mínimo 3 caracteres"
                else -> null
            },
            clave = when {
                estado.clave.isBlank() -> "Campo obligatorio"
                estado.clave.length < 4 -> "Mínimo 4 caracteres"
                else -> null
            },
            confirmarClave = when {
                estado.confirmarClave.isBlank() -> "Campo obligatorio"
                estado.confirmarClave != estado.clave -> "Las contraseñas no coinciden"
                else -> null
            }
        )
        _uiState.update { it.copy(errores = errores) }

        return errores.usuario == null &&
                errores.clave == null &&
                errores.confirmarClave == null
    }

    fun crear() {
        if (!validarFormato()) return

        val estado = _uiState.value
        val nombre = estado.usuario.trim()

        viewModelScope.launch {
            if (repository.existe(nombre)) {
                _uiState.update {
                    it.copy(errores = it.errores.copy(usuario = "Ese usuario ya existe"))
                }
            } else {
                repository.guardar(Usuario(usuario = nombre, clave = estado.clave))
                _uiState.value = CrearUsuarioUiState(usuarioCreado = nombre)
            }
        }
    }
}