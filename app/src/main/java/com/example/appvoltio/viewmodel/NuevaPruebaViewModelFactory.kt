package com.example.appvoltio.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appvoltio.data.AppDatabase
import com.example.appvoltio.repository.PruebaRepository

class NuevaPruebaViewModelFactory(
    app: Application,
    private val equipoId: Int
) : ViewModelProvider.Factory {

    private val repository by lazy {
        val dao = AppDatabase.obtener(app).pruebaDao()
        PruebaRepository(dao)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class <T>): T =
        NuevaPruebaViewModel(equipoId, repository) as T
}