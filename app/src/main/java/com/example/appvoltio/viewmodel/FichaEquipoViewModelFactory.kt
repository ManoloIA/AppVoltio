package com.example.appvoltio.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appvoltio.data.AppDatabase
import com.example.appvoltio.repository.EquipoRepository
import com.example.appvoltio.repository.PruebaRepository

class FichaEquipoViewModelFactory(
    app: Application,
    private val equipoId: Int
) : ViewModelProvider.Factory {

    private val database by lazy { AppDatabase.obtener(app) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        FichaEquipoViewModel(
            equipoId,
            EquipoRepository(database.equipoDao()),
            PruebaRepository(database.pruebaDao())
        ) as T
}