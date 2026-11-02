package com.example.appvoltio.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appvoltio.data.AppDatabase
import com.example.appvoltio.repository.UsuarioRepository

class LoginViewModelFactory(app: Application) : ViewModelProvider.Factory {

    private val repository by lazy {
        UsuarioRepository(AppDatabase.obtener(app).usuarioDao())
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        LoginViewModel(repository) as T
}