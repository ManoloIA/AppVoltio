package com.example.appvoltio.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appvoltio.data.AppDatabase
import com.example.appvoltio.repository.EquipoRepository

class RegistroEquipoViewModelFactory(app: Application) : ViewModelProvider.Factory {

    private val repository  by lazy {
        val dao = AppDatabase.obtener(app).equipoDao()
        EquipoRepository(dao)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        RegistroEquipoViewModel(repository) as T
}